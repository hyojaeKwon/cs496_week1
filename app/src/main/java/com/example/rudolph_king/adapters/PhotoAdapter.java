package com.example.rudolph_king.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.rudolph_king.R;

import java.util.ArrayList;

public class PhotoAdapter  extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    public interface OnListItemSelectedInterface {
        void onItemSelected(View view, int position);
    }

    private OnListItemSelectedInterface mListener;
    private ArrayList<String> mDataset;
    private Context mContext;

    public PhotoAdapter(Context context, ArrayList<String> myDataset, OnListItemSelectedInterface listener) {
        mDataset = myDataset;
        mContext = context;
        mListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String photo = mDataset.get(position);
        Glide.with(mContext)
            .load(photo)
            .thumbnail(0.5f)
            .into(holder.img_thumb);
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
        private RelativeLayout layout_gallery;
        private ImageView img_thumb;
        public ViewHolder(View convertView) {
            super(convertView);
            layout_gallery = (RelativeLayout) convertView.findViewById(R.id.layout_gallery);
            img_thumb = (ImageView) convertView.findViewById(R.id.imageView_gallery);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mListener.onItemSelected(view, position);

                    Log.d("test", "position = " + position);
                }
            });
        }
    }

}