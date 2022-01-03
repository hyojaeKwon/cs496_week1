package com.example.rudolph_king.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.Shops;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GitfAdapter extends RecyclerView.Adapter<GitfAdapter.ItemViewHolder> {

    // adapter에 들어갈 arraylist다
    private ArrayList<Gift> giftData = new ArrayList<Gift>();
    private Context context;

    public GitfAdapter(Context context,ArrayList<Gift> giftArrayList){
        this.context = context;
        this.giftData = giftArrayList;
    }
    //arraylist에 원소 추가하는 메서드
    void addItem(Gift gift){
        giftData.add(gift);
    }

    @NonNull
    @Override
    public GitfAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item,parent,false);
        return new ItemViewHolder(view);
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

    //RecyclerView의 핵심인 ViewHolder생성
    //subView를 set한다.

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1;
        private TextView tv_price;
        private TextView tv_company;
        private ImageView iv_picture;
        private TextView tag_number;

        //ItemViewHolder의 생성자
        ItemViewHolder(View view){
            super(view);
            tag_number = view.findViewById(R.id.tv_ranking);
            tv_company = view.findViewById(R.id.tv_company);
            tv1 = view.findViewById(R.id.tv_productName);
            tv_price = view.findViewById(R.id.tv_price);
            iv_picture = view.findViewById(R.id.imageView_product);
        }


        //상품 이름 집어넣는 코드
        void onBind(Gift gift,int index){
            index = index + 1;
            tag_number.setText("# " + Integer.toString(index));
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
        }
    }
}
