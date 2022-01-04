package com.example.rudolph_king.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.adapters.GitfAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

//import com.kakao.kakaolink.v2.KakaoLinkResponse;
//import com.kakao.kakaolink.v2.KakaoLinkService;

public class BuyItems extends AppCompatActivity {


    public int id;
    public  TextView tv_buy_name;
    public ImageView iv_product;
    public TextView tv_price;
    public ArrayList<Gift> giftArrayList;
    public View  pV;
    public Button searchBtn;
    public String buyUrl = "https://gift.kakao.com/product/";
    public ImageView kakaoBtn;
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
        pV = (View) findViewById(R.id.pressView);
        iv_product = (ImageView) findViewById(R.id.imageView2);
        searchBtn = (Button) findViewById(R.id.buy_btn);
        kakaoBtn = (ImageButton) findViewById(R.id.kakao_btn);

        Log.e("TEXT", (String) tv_buy_name.getText());
        setItems(id);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.e("printName", String.valueOf(tv_buy_name.getText()));

        pV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gift.kakao.com/product/"+id));
                startActivity(intent);
            }
        });
        kakaoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent msg = new Intent(Intent.ACTION_SEND);

                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_TEXT, "`"+tv_buy_name.getText()+"' 사주고 싶다. 한번 들어가봐\n" + "https://gift.kakao.com/product/"+id );
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "앱을 선택해 주세요"));
            }
        });
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