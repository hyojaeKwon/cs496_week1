package com.example.rudolph_king.adapters;

import static com.example.rudolph_king.R.color;
import static com.example.rudolph_king.R.id;
import static com.example.rudolph_king.R.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rudolph_king.R;
import com.example.rudolph_king.Shops;
import com.example.rudolph_king.activities.CallActivity;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements OnPersonItemClickListener {

    private  Context context;
    private  ArrayList<Shops> list;
    OnPersonItemClickListener listener;

    public CustomAdapter(Context context, ArrayList<Shops> shopList) {
        this.context = context;
        this.list = shopList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public static RecyclerView rvs;
        public final TextView tv_name;
        public final TextView tv_summary;
        public final ImageView iv_thumb;
        public final TextView iv_open;
        public View mView = null;


        //        public final TextView iv_tags;
        public ViewHolder(View view, final OnPersonItemClickListener listener) {
            super(view);
            // Define click listener for the ViewHolder's View
            tv_name = (TextView) view.findViewById(id.tv_ranking);
            tv_summary = (TextView) view.findViewById(id.tv_productName);
            iv_thumb = (ImageView) view.findViewById(id.imageView_product);
            iv_open = (TextView) view.findViewById(id.textView_isOpen);
            rvs = view.findViewById(id.rvChapters);
            mView = view;

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Context context = view.getContext();

                    //recyclerview??? index????????? ????????? activity ??????
                    if(position != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, CallActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.e("position",Integer.toString(position));
                        intent.putExtra("pos",position);

                        context.startActivity(intent);

                    }
                }
            });

        }


    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layout.row_item, viewGroup, false);

        return new ViewHolder(view,this);
    }

    //????????? ?????? ??????????????? ??????

    public void filterList(ArrayList<Shops> filteredShopList) {
        this.list = filteredShopList;
        notifyDataSetChanged();
    }
    // Replace the contents of a view (invoked by the layout manager)


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        final Shops shop = (Shops) list.get(position);
        viewHolder.tv_name.setText(shop.getT());
        viewHolder.tv_summary.setText(shop.getPhone());


        if (shop.getIsOpen() == true){
            viewHolder.iv_open.setText("?????? ???");
            viewHolder.iv_open.setTextColor(ContextCompat.getColor(context, color.open));
        } else {
            viewHolder.iv_open.setText("?????? ??????");
            viewHolder.iv_open.setTextColor(ContextCompat.getColor(context, color.not_open));
        }
        
        Glide
                .with(context)
                .load(shop.getThumb_url())
                .centerCrop()
                .apply(new RequestOptions().override(250, 250))
                .into(viewHolder.iv_thumb);
        Log.e("tag", shop.getThumb_url());
//        viewHolder.iv_thumb.setImageResource(drawable.img);

        ArrayList<String> tag = shop.getTags();
        ArrayList<TagData> td = new ArrayList<>();

        for (int i = 0; i < tag.size(); i++) {
            TagData tagData = new TagData(tag.get(i));
            td.add(tagData);
        }

        TagAdapter ta = new TagAdapter(context, td);
        LinearLayoutManager manager = new LinearLayoutManager(context);

        ViewHolder.rvs.setLayoutManager(manager);
        ViewHolder.rvs.setAdapter(ta);

        if(position % 2 == 1) {
            viewHolder.mView.findViewById(id.row_item_layout).setBackgroundResource(R.color.gray_000);
        }
    }
    // Return the size of your dataset (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return list.size();
    }


    //???????????? ???????????? ??????
    public void OnPersonItemClickListener(OnPersonItemClickListener listener) {
        this.listener = listener;
    }
    //item?????? ??? ??????
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null){
            listener.onItemClick(holder,view,position);
        }
    }
}