package com.example.rudolph_king.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.Shops;
import com.example.rudolph_king.activities.MainActivity;
import com.example.rudolph_king.fragments.Fragment3;

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
        holder.onBind(giftData.get(position));
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
        private TextView tv2;
        private TextView tv3;
        private ImageButton likeBtn;

        //ItemViewHolder의 생성자
        ItemViewHolder(View view){
            super(view);

            tv1 = view.findViewById(R.id.tv_ranking);
            likeBtn = view.findViewById(R.id.f3_like);
        }


        //상품 이름 집어넣는 코드
        void onBind(Gift gift){
            tv1.setText(gift.getProductName());
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
}
