package com.example.rudolph_king.adapters;

import static com.example.rudolph_king.fragments.Fragment2.refreshAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.R;
import com.example.rudolph_king.activities.MainActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public interface OnListItemSelectedInterface {
        void onItemSelected(View view, int position);
    }

    private OnListItemSelectedInterface mListener;
    private ArrayList<GalleryImage> mDataset;
    private Context mContext;

    public ReviewAdapter(Context context, ArrayList<GalleryImage> myDataset, OnListItemSelectedInterface listener) {
        mDataset = myDataset;
        mContext = context;
        mListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.review_item, parent, false);
        Log.e("onCreateViewHolder", String.valueOf(true));
        return new ViewHolder(convertView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GalleryImage review = mDataset.get(position);

        holder.photoAdapter = new PhotoAdapter(mContext, review.getUriList());
        holder.mRecyclerView.setAdapter(holder.photoAdapter);
        holder.mReviewName.setText(review.getReviewName());
        holder.mReviewMembers.setText(review.getReviewMembers());
        holder.mReviewDate.setText(review.getReviewDate());
        holder.mReviewName.setText(review.getReviewName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        RecyclerView mRecyclerView;
        TextView mReviewName;
        TextView mReviewMembers;
        TextView mReviewDate;
        PhotoAdapter photoAdapter;
        public ViewHolder(View convertView) {
            super(convertView);

            mReviewName = (TextView) convertView.findViewById(R.id.review_name);
            mReviewMembers = (TextView) convertView.findViewById(R.id.review_members);
            mReviewDate = (TextView) convertView.findViewById(R.id.review_date);
            mRecyclerView = (RecyclerView) convertView.findViewById(R.id.review_photo);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);

            convertView.setOnClickListener(new View.OnClickListener() {
                // gallery photo click
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mListener.onItemSelected(view, position);
                    Log.d("test", "position = " + position);
                }
            });

            convertView.setOnCreateContextMenuListener(this);

//            convertView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    int position = getAdapterPosition();
//                    android.app.AlertDialog.Builder adb = new AlertDialog.Builder(mContext, R.style.AlertDialog_AppCompat_Light);
//                    adb.setTitle("Delete")
//                            .setNeutralButton("CONFIRM", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
////                                    String imagePath = mDataset.get(position).getPath();
//                                    mDataset.remove(position);
//
////                                    File file = new File(imagePath).getAbsoluteFile();
////
////                                    if(file.exists()){
////                                        System.gc();
////                                        System.runFinalization();
////                                        boolean ch = file.delete();
////                                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
////                                    }
//                                    MainActivity.updateJSONImages(null, position);
//                                    refreshAdapter();
//                                    // adapter.notifyDataSetChanged();
//                                }
//                            })
//                            .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            });
//                    AlertDialog finalDialog = adb.create();
//                    finalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                        @Override
//                        public void onShow(DialogInterface arg0) {
//                            finalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6E6557"));
//                            finalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#6E6557"));
//                            finalDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#6E6557"));
//                        }
//                    });
//                    finalDialog.show();
//                    return true;
//                }
//            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem Edit = contextMenu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = contextMenu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
         private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
             public boolean onMenuItemClick(MenuItem item) {
                 int position = getAdapterPosition();
                 switch (item.getItemId()) {
                     case 1001:  // 5. 편집 항목을 선택시
                         AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                         View view = LayoutInflater.from(mContext)
                                 .inflate(R.layout.review_info_edit, null, false);
                         builder.setView(view);
                         final Button ButtonSubmit = (Button) view.findViewById(R.id.button_review_submit);
                         final EditText editReviewName = (EditText) view.findViewById(R.id.edit_review_name);
                         final EditText editReviewMembers = (EditText) view.findViewById(R.id.edit_review_members);

                         // 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여주기
                         editReviewName.setText(mDataset.get(position).getReviewName());
                         editReviewMembers.setText(mDataset.get(position).getReviewMembers());

                         final AlertDialog dialog = builder.create();
                         ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                             // 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로 변환
                             public void onClick(View v) {
                                 String reviewName = editReviewName.getText().toString();
                                 String reviewMembers = editReviewMembers.getText().toString();
                                 dialog.dismiss();

                                 GalleryImage gi = mDataset.get(position);
                                 gi.setReviewName(reviewName);
                                 gi.setReviewMembers(reviewMembers);
                                 MainActivity.reviewList.set(position, gi);
                                 refreshAdapter();
                                 MainActivity.updateJSONImages(gi, position);
                             }
                         });
                         dialog.show();
                         break;

                     case 1002:
                         android.app.AlertDialog.Builder adb = new AlertDialog.Builder(mContext, R.style.AlertDialog_AppCompat_Light);
                         adb.setTitle("Delete")
                                .setNeutralButton("CONFIRM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDataset.remove(position);
                                        MainActivity.updateJSONImages(null, position);
                                        refreshAdapter();
                                    }
                                })
                                .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog finalDialog = adb.create();
                        finalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                finalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6E6557"));
                                finalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#6E6557"));
                                finalDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#6E6557"));
                            }
                        });
                        finalDialog.show();
                         break;
                 }
                 return true;
             }
         };
    }
}