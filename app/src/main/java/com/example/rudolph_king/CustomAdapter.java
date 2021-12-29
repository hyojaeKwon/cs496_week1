package com.example.rudolph_king;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Fragment1.Actor> list;

    public CustomAdapter(Context context, ArrayList<Fragment1.Actor> actors) {
        this.context = context;
        this.list = actors;
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

        final Fragment1.Actor actor = (Fragment1.Actor) list.get(position);
        viewHolder.tv_name.setText(actor.getName());
        viewHolder.tv_summary.setText(actor.getSummary());
        Glide
                .with(context)
                .load(actor.getThumb_url())
                .centerCrop()
                .apply(new RequestOptions().override(250, 250))
                .into(viewHolder.iv_thumb);
        Log.e("image load", actor.getThumb_url());
        viewHolder.tv_name.setTag(actor.getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
