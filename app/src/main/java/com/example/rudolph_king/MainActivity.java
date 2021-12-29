package com.example.rudolph_king;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    private Context mContext;

    public JSONObject reading(Context context){
        this.mContext = context;
        AssetManager assetManager = getResources().getAssets();
        InputStream source = null;
        JSONObject JObject = null;
        try{
            source = assetManager.open("shop.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(source));

            String strResult = "";
            String line = "";

            while((line=reader.readLine()) != null){
                strResult += line;
            }
            JObject = new JSONObject(strResult);
            return JObject;
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return JObject;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);

        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        //json file 받기


        // connect view pager with tab layout
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

    }
}