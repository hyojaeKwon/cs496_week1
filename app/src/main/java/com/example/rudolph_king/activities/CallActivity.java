package com.example.rudolph_king.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rudolph_king.Shops;
import com.example.rudolph_king.fragments.Fragment1;

import com.example.rudolph_king.R;

import java.util.ArrayList;

public class CallActivity extends AppCompatActivity {

    public TextView tv_isOpen;
    public TextView tv_name;
    public TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        setItems();
    }
    Intent intent = new Intent();
    String name = "pos";
    int pos = intent.getIntExtra(name,0);
//    Log.e("nowList", Integer.toString(pos));
    Fragment1 f1  = new Fragment1();
    private boolean chooseOriginal = f1.isFilteredListEmpty();

    public void setItems(){
        this.tv_isOpen = (TextView) findViewById(R.id.call_isOpen);
        this.tv_name = (TextView) findViewById(R.id.title_of_shop_detail);
        this.tv_phone = (TextView) findViewById(R.id.call_phone);

        ArrayList<Shops> nowList = new ArrayList<>();
        if (getChoose() == true){
            nowList = f1.getShopList();
        } else {
            nowList = f1.getFilteredList();
        }
        Log.e("nowList", Integer.toString(nowList.size()));
//        if (nowList.get(pos).getIsOpen() == true){
//            tv_isOpen.setText("영업 중");
//        } else {
//            tv_isOpen.setText("영업 종료");
//        }
//        tv_name.setText(nowList.get(pos).getT());
//        tv_phone.setText(nowList.get(pos).getPhone());

    }
    public boolean getChoose(){
        if (chooseOriginal == true){
            return true;
        } else {
            return false;
        }
    }
    public int getPos(){
        return pos;
    }












}