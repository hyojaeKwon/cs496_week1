package com.example.rudolph_king.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.R;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoactivity_main);

        // setting action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("게시물");
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setCustomView(R.layout.action_bar_photo);


        ImageView image = (ImageView) findViewById(R.id.imageView_detail);
        Intent intent = getIntent();
        int position = intent.getIntExtra("pos", 0);
        GalleryImage review = MainActivity.reviewList.get(position);
//        Glide.with(this)
//            .load(img)
//            .thumbnail(0.5f)
//            .into(image);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }


}
