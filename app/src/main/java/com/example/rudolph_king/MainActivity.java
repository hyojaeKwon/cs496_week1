package com.example.rudolph_king;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import 	android.content.res.AssetManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;


public class MainActivity extends AppCompatActivity {
    public static AssetManager assetManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);

        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        //json file 받기
        assetManager = getResources().getAssets();

        // connect view pager with tab layout
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

    }
}