package com.example.rudolph_king;

import android.graphics.Bitmap;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryImage {
    private ArrayList<Uri> uriList;

//    public GalleryImage(JSONObject jsonText) throws JSONException {
//        // this.uriList = jsonText.getString("uriList");
//        JSONArray jsonList = jsonText.getJSONArray("uriList");
//
//        //uriList array list 생성
//        uriList = new ArrayList<Uri>();
//        for(int i = 0 ; i < jsonList.length() ; i++){
//            Uri uri;
//            uri = (Uri) jsonList.get(i);
//            //찍어보는 방법
////            Log.e("check", tag.toString());
//            this.uriList.add(uri);
//        }
//    }

    public void setUriList(ArrayList<Uri> uri) { this.uriList = uri ;}
    public ArrayList<Uri> getUriList() {return this.uriList;}
}
