package com.example.rudolph_king.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import 	android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.R;
import com.example.rudolph_king.adapters.VPAdapter;
import com.example.rudolph_king.fragments.Fragment2;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private long backKeyPressedTime = 0;
    private Toast toast;
    public static ArrayList<GalleryImage> reviewList = new ArrayList<GalleryImage>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_main);

        ViewPager vp = findViewById(R.id.viewpager);

        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        //json file 받기
        AssetManager assetManager = getResources().getAssets();

        // connect view pager with tab layout
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        } else {
            finish();;
            toast.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // upload photo from gallery
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                if (data == null){   // 어떤 이미지도 선택하지 않은 경우
                    Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                    // Log.e("no choice: ", String.valueOf(uriList));
                }
                else{   // 이미지를 선택한 경우
                    ArrayList<Uri> imageUriList = new ArrayList<Uri>();
                    if (data.getClipData() == null) {
                        // 이미지를 한 장만 선택한 경우
                        Uri imageUri = data.getData();
                        imageUriList.add(imageUri);
                        // Log.e("single choice: ", String.valueOf(uriList));
                    } else {
                        // 이미지를 여러 장 선택한 경우
                        ClipData clipData = data.getClipData();
                        if (clipData.getItemCount() > 5) {
                            Toast.makeText(this, "사진은 5장까지 선택 가능합니다", Toast.LENGTH_LONG).show();
                        } else {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();

                                try {
                                    imageUriList.add(imageUri);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }

                    GalleryImage newReview = new GalleryImage();
                    newReview.setUriList(imageUriList);
                    reviewList.add(0, newReview);
                }

                Fragment2.refreshAdapter();
            }
        }
    }
}