package com.example.rudolph_king.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.activities.BuyItems;
import com.example.rudolph_king.Shops;
import com.example.rudolph_king.activities.MainActivity;
import com.example.rudolph_king.fragments.Fragment3;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GitfAdapter extends RecyclerView.Adapter<GitfAdapter.ItemViewHolder> {

    // adapter에 들어갈 arraylist다
    public static ArrayList<Gift> giftData = new ArrayList<Gift>();
    private Context context;

    public GitfAdapter(Context context,ArrayList<Gift> giftArrayList){
        this.context = context;
        this.giftData = giftArrayList;
    }

    public GitfAdapter() {

    }

    //arraylist에 원소 추가하는 메서드
    void addItem(Gift gift){
        giftData.add(gift);
    }

    @NonNull
    @Override
    public GitfAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item,parent,false);
        return new ItemViewHolder(view, this);
    }
    public void filterList(ArrayList<Gift> filteredShopList) {
        this.giftData = filteredShopList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull GitfAdapter.ItemViewHolder holder, int position) {
        holder.onBind(giftData.get(position),position);
    }

    @Override
    public int getItemCount() {
        return giftData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    //RecyclerView의 핵심인 ViewHolder생성
    //subView를 set한다.

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1;
        private TextView tv_price;
        private TextView tv_company;
        private ImageView iv_picture;
        private TextView tag_number;
        private View mView;

        private ImageButton likeBtn;

        //ItemViewHolder의 생성자
        ItemViewHolder(View view, final GitfAdapter listener){
            super(view);
            mView = view;
            tag_number = view.findViewById(R.id.tv_ranking);
            tv_company = view.findViewById(R.id.tv_company);
            tv1 = view.findViewById(R.id.tv_productName);
            tv_price = view.findViewById(R.id.tv_price);
            iv_picture = view.findViewById(R.id.imageView_product);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    Context context = view.getContext();

                    if(position != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, BuyItems.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("id",giftData.get(position).getId());
                        Activity activity = (Activity) context;
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                    }
                }
            });

            likeBtn = view.findViewById(R.id.f3_like);
        }


        //상품 이름 집어넣는 코드
        void onBind(Gift gift,int index){
            tag_number.setText("# " + Integer.toString(index + 1));
            if(index % 2 == 1) {
                mView.findViewById(R.id.f3_item_layout).setBackgroundResource(R.color.gray_000);
            }
            tv_company.setText(gift.getCompany());
            tv1.setText(gift.getProductName());
            int price = gift.getPrice();
            DecimalFormat df = new DecimalFormat("#,###");
            tv_price.setText(df.format(price)+"원");
            Glide
                    .with(context)
                    .load(gift.getPictureUrl())
                    .centerCrop()
                    .apply(new RequestOptions().override(250, 250))
                    .into(iv_picture);
            if (Fragment3.mWishList.contains(gift.getId())) {
                likeBtn.setSelected(true);
            }
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeBtn.setSelected(!likeBtn.isSelected());

                    if (likeBtn.isSelected()) {
                        Fragment3.mWishList.add((Integer) gift.getId());
                        Log.e("Gift Added", String.valueOf(Fragment3.mWishList.size()));
                        Log.e("List", Fragment3.mWishList.toString());
                    } else {
                        Fragment3.mWishList.remove((Integer) gift.getId());
                        Log.e("Gift Removed", String.valueOf(Fragment3.mWishList.size()));
                        Log.e("List", Fragment3.mWishList.toString());
                    }
                }
            });
        }

    }

    public ArrayList<Gift> getGiftData(){
        return giftData;
    }

}
