package com.example.rudolph_king.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import 	android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.adapters.VPAdapter;
import com.example.rudolph_king.fragments.Fragment2;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static Context mContext;
    private long backKeyPressedTime = 0;
    private Toast toast;
    public static ArrayList<GalleryImage> reviewList = new ArrayList<GalleryImage>();

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = this;
        getHashKey();
        setContentView(R.layout.activity_main);

        // setting action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_main);

        ViewPager vp = findViewById(R.id.viewpager);

        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        // 갤러리를 위한 json file 받기
        // AssetManager assetManager = getResources().getAssets();

        // connect view pager with tab layout
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        tab.getTabAt(0).setIcon(R.drawable.ic_shops);
        tab.getTabAt(1).setIcon(R.drawable.ic_gallery);
        tab.getTabAt(2).setIcon(R.drawable.ic_present);
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
                            return;
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

                    // create new review
//                    GalleryImage newReview = new GalleryImage();
//                    newReview.setUriList(imageUriList);
//                    // reviewList.add(0, newReview);
//                    updateJSONImages(newReview);
                    Fragment2.setReviewInfo("", "", "", "", imageUriList, true);
                }
            }
        }
    }

    public static void updateJSONImages(GalleryImage review, int position) {
        JsonRead jr = new JsonRead();
        JSONObject jo = jr.reading(mContext, "images.json");
        FileInputStream fis = null;
        String fileName = "images.json";
        try {
            fis = mContext.openFileInput(fileName);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            } finally {
                String contents = stringBuilder.toString();
                jo = new JSONObject(contents);
            }
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray ja = jo.getJSONArray("Reviews");
            if (position < 0) {
                JSONObject jsonObject = new JSONObject();//배열 내에 들어갈 json
                jsonObject.put("uriList", review.getUriList().toString());
                jsonObject.put("name", review.getReviewName());
                jsonObject.put("members", review.getReviewMembers());
                jsonObject.put("date", review.getReviewDate());
                jsonObject.put("desc", review.getReviewDescription());
                ja.put(jsonObject);
                Log.e("newAdded", String.valueOf(ja.length()));
            } else {
                if (review != null) {
                    JSONObject jsonObject = new JSONObject();//배열 내에 들어갈 json
                    jsonObject.put("uriList", review.getUriList().toString());
                    jsonObject.put("name", review.getReviewName());
                    jsonObject.put("members", review.getReviewMembers());
                    jsonObject.put("date", review.getReviewDate());
                    jsonObject.put("desc", review.getReviewDescription());
                    ja.put(ja.length() - position - 1, jsonObject);
                    Log.e("newAdded", String.valueOf(ja.length()));
                } else {
                    ja.remove(ja.length() - position - 1);
                    Log.e("deleted", String.valueOf(ja.length()));
                }
            }
            jo.put("Reviews", ja);

            try {
                FileOutputStream fileOutputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                //Convert JSON String to Bytes and write() it
                fileOutputStream.write(jo.toString().getBytes());
                //Finally flush and close FileOutputStream
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}