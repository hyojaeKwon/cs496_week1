package com.example.rudolph_king.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.adapters.GitfAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BuyItems extends AppCompatActivity {


    public int id;
    public  TextView tv_buy_name;
    public ImageView iv_product;
    public TextView tv_price;
    public ArrayList<Gift> giftArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_items);

        Intent intent = new Intent(getApplicationContext(), GitfAdapter.class);
        String name = "id";
        intent = getIntent();
        id = intent.getIntExtra(name,0);
        Log.d("name",Integer.toString(id));
        this.tv_buy_name = (TextView) findViewById(R.id.buy_name);
        tv_price = (TextView) findViewById(R.id.buy_price);
        iv_product = (ImageView) findViewById(R.id.imageView2);
        Log.e("TEXT", (String) tv_buy_name.getText());
        setItems(id);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.e("printName", String.valueOf(tv_buy_name.getText()));

    }

    public void setItems(int id){
        GitfAdapter ga = new GitfAdapter();
        giftArrayList = ga.getGiftData();
        Log.e("sizeOfgiftAL",Integer.toString(giftArrayList.size()));
        for(int i = 0 ; i < giftArrayList.size() ; i++){
            Log.d("name",Integer.toString(giftArrayList.get(i).getId()));
            if(id == giftArrayList.get(i).getId()){
                Log.d("name",giftArrayList.get(i).getProductName());
                tv_buy_name.setText(giftArrayList.get(i).getProductName());
                int price = giftArrayList.get(i).getPrice();
                DecimalFormat df = new DecimalFormat("#,###");
                tv_price.setText(df.format(price)+"원");
                Glide
                        .with(this)
                        .load(giftArrayList.get(i).getPictureUrl())
                        .centerCrop()
                        .apply(new RequestOptions().override(250, 250))
                        .into(iv_product);
                break;
            }
        }
    }
}