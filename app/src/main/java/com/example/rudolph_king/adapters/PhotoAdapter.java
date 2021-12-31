package com.example.rudolph_king.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rudolph_king.R;

import java.io.File;
import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
//    public interface OnListItemSelectedInterface {
//        void onItemSelected(View view, int position);
//    }

    //private OnListItemSelectedInterface mListener;
    private ArrayList<Uri> mDataset;
    private Context mContext;

    public PhotoAdapter(Context context, ArrayList<Uri> myDataset) {//OnListItemSelectedInterface listener) {
        mDataset = myDataset;
        mContext = context;
        //mListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false);
        Log.e("onCreateViewHolder", String.valueOf(true));
        return new ViewHolder(convertView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri photo = mDataset.get(position);
        Glide.with(mContext)
            .load(photo)
            .thumbnail(0.5f)
            .into(holder.img_thumb);
        Log.e("viewhodler", photo.getPath());
//        holder.layout_gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnItemClickListener.onItemClick(v, albumVO);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout_gallery;
        private ImageView img_thumb;
        public ViewHolder(View convertView) {
            super(convertView);
            layout_gallery = (ConstraintLayout) convertView.findViewById(R.id.layout_gallery);
            img_thumb = (ImageView) convertView.findViewById(R.id.imageView_gallery);

//            convertView.setOnClickListener(new View.OnClickListener() {
//                // gallery photo click
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    mListener.onItemSelected(view, position);
//                    Log.d("test", "position = " + position);
//                }
//            });
//
//
//            convertView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    int position = getAdapterPosition();
//                    AlertDialog.Builder adb = new AlertDialog.Builder(mContext, R.style.AlertDialog_AppCompat_Light);
//                    adb.setTitle("Delete")
//                            .setNeutralButton("CONFIRM", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    String imagePath = mDataset.get(position).getPath();
//                                    mDataset.remove(position);
//
//                                    File file = new File(imagePath).getAbsoluteFile();
//
//                                    if(file.exists()){
//                                        System.gc();
//                                        System.runFinalization();
//                                        boolean ch = file.delete();
//                                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
//                                    }
//
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
    }

}