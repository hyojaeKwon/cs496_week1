package com.example.rudolph_king.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rudolph_king.R;
import com.example.rudolph_king.Shops;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements AdapterView.OnItemClickListener {

    private Context context;
    private ArrayList<Shops> list;

    public CustomAdapter(Context context, ArrayList<Shops> shopList) {
        this.context = context;
        this.list = shopList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = (String) view.findViewById(R.id.textView_name).getTag().toString();
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

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tv_name = (TextView) view.findViewById(R.id.textView_name);
            tv_summary = (TextView) view.findViewById(R.id.textView_summary);
            iv_thumb = (ImageView) view.findViewById(R.id.imageView_thumb);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        final Shops shop = (Shops) list.get(position);
        viewHolder.tv_name.setText(shop.getTitle());
        viewHolder.tv_summary.setText(shop.getPhone());
        Glide
            .with(context)
            .load(shop.getThumb_url())
            .centerCrop()
            .apply(new RequestOptions().override(180, 180))
            .into(viewHolder.iv_thumb);
//        Log.e("image load", String.valueOf(position));
        viewHolder.tv_name.setTag(shop.getTitle());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
