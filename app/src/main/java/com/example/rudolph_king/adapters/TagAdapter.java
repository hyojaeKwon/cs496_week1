package com.example.rudolph_king.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rudolph_king.R;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.CustomViewHolder>{
    private final int MAX_LENGTH = 20;
    private final Context context;
    private ArrayList<TagData> arrayList;
    private LayoutInflater inflater;
    private LinearLayout listView;

    public TagAdapter(Context context, ArrayList<TagData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = LayoutInflater.from(context);
    }
    
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        view = inflater.inflate(R.layout.column_tags,parent,false);

        return new CustomViewHolder(view);
    }
    public void onBindViewHolder(CustomViewHolder holder, int position){
        int count = 0;
        for(int i = 0 ; i < arrayList.size() ; i++){
            TextView tv = new TextView(context);
            int currentCount = arrayList.get(i).getTv_tag().toString().length();
            if (count + currentCount <= MAX_LENGTH){
                tv.setText(arrayList.get(i).getTv_tag().toString());
                count = count + currentCount;
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                param.leftMargin = 12;
                tv.setLayoutParams(param);
                tv.setPadding(4, 2, 4, 2);
                tv.setBackgroundColor(0xFFF0F0F0);
                holder.tv_tag.addView(tv);

            }
//            else {
////                String text = "\n" + arrayList.get(i).getTv_tag().toString();
////                Log.e("count_num", text);
////                tv.setText(text);
//            }


            Log.e("count_num", String.valueOf(count));
            Log.e("tag_text",arrayList.get(i).getTv_tag().toString());
        }
//        holder.tv_tag.setText(arrayList.get(position).getTv_tag().toString());
    }

//
//    public TagAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
//
//        CustomViewHolder holder = new CustomViewHolder(view);
//
//
//        return holder;
//    }

//    @Override
//    public void onBindViewHolder(@NonNull TagAdapter.CustomViewHolder holder, int position) {
//        holder.tv_tag.setText(arrayList.get(position).toString());
//
//        holder.itemView.setTag(position);
//
//
//    }

    @Override
    public int getItemCount() {
        return (null!= arrayList?arrayList.size():0);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout tv_tag;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.tv_tag = itemView.findViewById(R.id.tv_tag_id);

        }
    }
}