package com.example.rudolph_king.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import 	android.content.res.AssetManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rudolph_king.R;
import com.example.rudolph_king.adapters.VPAdapter;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);

        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        //json file 받기
        AssetManager assetManager = getResources().getAssets();

        // connect view pager with tab layout
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

    }

//    public void showDetails(View view) {
//        Log.e("Pressed", String.valueOf(true));
////        Intent intent = new Intent(this, PhotoActivity.class);
////        intent.putExtra("img", 1);
////        startActivity(intent);
//    }
}