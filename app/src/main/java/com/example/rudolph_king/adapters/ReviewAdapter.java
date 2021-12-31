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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.R;
import com.example.rudolph_king.activities.MainActivity;

import java.util.ArrayList;

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
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        PhotoAdapter photoAdapter;
        public ViewHolder(View convertView) {
            super(convertView);
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


            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    android.app.AlertDialog.Builder adb = new AlertDialog.Builder(mContext, R.style.AlertDialog_AppCompat_Light);
                    adb.setTitle("Delete")
                            .setNeutralButton("CONFIRM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

//                                    String imagePath = mDataset.get(position).getPath();
                                    mDataset.remove(position);

//                                    File file = new File(imagePath).getAbsoluteFile();
//
//                                    if(file.exists()){
//                                        System.gc();
//                                        System.runFinalization();
//                                        boolean ch = file.delete();
//                                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
//                                    }
                                    refreshAdapter();
                                    // adapter.notifyDataSetChanged();
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
                    return true;
                }
            });
        }
    }

}