package com.example.rudolph_king;

import android.app.Activity;
import android.util.Log;

import org.json.*;

import java.util.ArrayList;

public class Shops extends Activity {

    private String title;
    private boolean isOpen;
    private String phone;
    private int thumb_url;
    private ArrayList<String> tags;

    public Shops(JSONObject jsonText) throws JSONException {
        this.title = jsonText.getString("name");
        this.isOpen = jsonText.getBoolean("isOpen");
        this.phone = jsonText.getString("phone");
//        this.thumb_url = jsonText.getString("thumb_url");
        JSONArray JTags = jsonText.getJSONArray("tag");

        //Tags array list 생성
        tags = new ArrayList<String>();
        for(int i = 0 ; i < JTags.length() ; i++){
            String tag;
            tag = JTags.getString(i);
            //찍어보는 방법
//            Log.e("check", tag.toString());
            this.tags.add(tag);
        }
//        this.thumb_url = getResources().getIdentifier("com.example.rudolph_king:drawalbe/img",null,null);
    }

    public String getT(){
        return title;
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
    public int getThumb_url() { return thumb_url;}
}

