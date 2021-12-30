package com.example.rudolph_king.adapters;

import static com.example.rudolph_king.R.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rudolph_king.R;
import com.example.rudolph_king.Shops;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements AdapterView.OnItemClickListener {

    private final Context context;
    private final ArrayList<Shops> list;

    public CustomAdapter(Context context, ArrayList<Shops> shopList) {
        this.context = context;
        this.list = shopList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = (String) view.findViewById(id.textView_name).getTag().toString();
        Toast.makeText(this.context, "Clicked: " + i +" " + selectedItem, Toast.LENGTH_SHORT).show();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv_name;
        public final TextView tv_summary;
        public final ImageView iv_thumb;
        public final TextView iv_open;
        public final TextView iv_tags;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tv_name = (TextView) view.findViewById(id.textView_name);
            tv_summary = (TextView) view.findViewById(id.textView_summary);
            iv_thumb = (ImageView) view.findViewById(id.imageView_thumb);
            iv_open = (TextView) view.findViewById(id.textView_isOpen);
            iv_tags = (TextView) view.findViewById(id.textview_tag_list);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layout.row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        final Shops shop = (Shops) list.get(position);
        viewHolder.tv_name.setText(shop.getT());
        viewHolder.tv_summary.setText(shop.getPhone());
        if(shop.getIsOpen()){
            viewHolder.iv_open.setText("영업 중");
            viewHolder.iv_open.setTextColor(ContextCompat.getColor(context,R.color.open));
        }else{
            viewHolder.iv_open.setText("영업 종료");
            viewHolder.iv_open.setTextColor(ContextCompat.getColor(context, color.not_open));        }
//        Glide
//                .with(context)
//                .load(shop.getThumb_url())
//                .centerCrop()
//                .apply(new RequestOptions().override(250, 250))
//                .into(viewHolder.iv_thumb);
////        Log.e( "tag",shop.getThumb_url().toString());
        viewHolder.iv_thumb.setImageResource(drawable.img);

        ArrayList<String> tagList = new ArrayList<String>();
        tagList = shop.getTags();
        String tagString = new String();
        //해시태그 개별 textview로 구성하기
        for(int i=0; i<tagList.size();i++){
            tagString = tagString + tagList.get(i) + " ";
        }
        viewHolder.iv_tags.setText(tagString);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
