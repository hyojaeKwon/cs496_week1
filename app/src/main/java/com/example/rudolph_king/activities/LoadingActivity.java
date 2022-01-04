package com.example.rudolph_king.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rudolph_king.R;

public class LoadingActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView splashGif = (ImageView) findViewById(R.id.loading_pict);
//        GlideDrawableImageViewTarget gif = new GlideDrawableImageViewTarget(splashGif);
        Glide.with(this).load(R.drawable.loading).into(splashGif);

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1900);
    }
}
