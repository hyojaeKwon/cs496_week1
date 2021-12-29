package com.example.rudolph_king;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonRead extends AppCompatActivity {
    private JSONArray JArray;
    public JsonRead(){
        InputStream inputStream = null;

        try{
            InputStream is = MainActivity.assetManager.open("Numbers.json", AssetManager.ACCESS_BUFFER);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String strResult = "";
            String line = "";

            while((line=reader.readLine()) != null){
                strResult+=line;
            }
;
            this.JArray = new JSONArray(strResult);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
    public JSONArray getJArray(){
        return this.JArray;
    }

}
