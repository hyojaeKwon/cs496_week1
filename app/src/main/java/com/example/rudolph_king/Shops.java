package com.example.rudolph_king;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;

public class Shops extends Activity {

    private String title;
    private boolean isOpen;
    private String phone;
    private String thumb_url;
    private int open;
    private int close;
    private ArrayList<String> tags;
    private double latitude;
    private double longitude;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Shops(JSONObject jsonText) throws JSONException {
        
        //JSON Parse하는 코드
        this.title = jsonText.getString("name");
        this.open = jsonText.getInt("open");
        this.close = jsonText.getInt("close");
        this.phone = jsonText.getString("phone");
        this.thumb_url = jsonText.getString("thumb_url");
        this.latitude = jsonText.getDouble("placeWi");
        this.longitude = jsonText.getDouble("placeKy");
        JSONArray JTags = jsonText.getJSONArray("tag");

        
        //현재의 시간을 LocalTime을 이용하여 받는다.
        //현재 시간과 가게 영업 시간을 비교하여 open상태 결정 후 isOpen에 bool로 대입.
        LocalTime now = LocalTime.now();
        int nowHour = now.getHour();
        if (nowHour>=open && nowHour<close){
            this.isOpen = true;
        } else {
            this.isOpen = false;
        }

        //Tags array list 생성
        tags = new ArrayList<String>();
        for(int i = 0 ; i < JTags.length() ; i++){
            String tag;
            tag = JTags.getString(i);
            //tags라는 linear layout에 append
            this.tags.add(tag);
        }
    }


    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public String getT(){
        return title;
    }
    public int getOpen(){
        return this.open;
    }
    public int getClose(){
        return this.close;
    }
    public boolean getIsOpen(){
        return isOpen;
    }
    public String getPhone(){
        return phone;
    }
    public ArrayList<String> getTags(){
        return tags;
    }
    public String getThumb_url() { return thumb_url;}
}

