package com.example.rudolph_king;

import org.json.*;

import java.util.ArrayList;

public class Shops {

    private String title;
    private boolean isOpen;
    private String phone;
    private String thumb_url;
    private ArrayList<String> tags;

    public Shops(JSONObject jsonText) throws JSONException {
        this.title = jsonText.getString("name");
        this.isOpen = jsonText.getBoolean("isOpen");
        this.phone = jsonText.getString("phone");
        this.thumb_url = jsonText.getString("thumb_url");
        JSONArray JTags = jsonText.getJSONArray("tag");

        for(int i = 0 ; i < JTags.length() ; i++){
            String tag;
            tag = JTags.getString(i);
            this.tags.add(tag);
        }
    }
    public String getTitle(){
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
    public String getThumb_url() { return thumb_url;}
}

