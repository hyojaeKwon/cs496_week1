package com.example.rudolph_king.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rudolph_king.R;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoactivity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView image = (ImageView) findViewById(R.id.imageView_detail);
        Intent intent = getIntent();
        int position = intent.getIntExtra("pos", 0);
        String img = intent.getStringExtra("img");
        Glide.with(this)
            .load(img)
            .thumbnail(0.5f)
            .into(image);
    }


}
