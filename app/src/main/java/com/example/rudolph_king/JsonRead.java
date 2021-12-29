package com.example.rudolph_king;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonRead extends AppCompatActivity  {

    TextView txtData;
    AssetManager assetManager;


    public JsonRead(){}


//    public JSONObject create(Bundle savedInstanceState){
//
//         AssetManager assetManager = getResources().getAssets();
//         InputStream source = null;
//         JSONObject JObject = null;
//         try{
//             source = assetManager.open("shop.json");
//             BufferedReader reader = new BufferedReader(new InputStreamReader(source));
//
//             String strResult = "";
//             String line = "";
//
//             while((line=reader.readLine()) != null){
//                 strResult += line;
//             }
//             JObject = new JSONObject(strResult);
//             return JObject;
//         }catch (IOException | JSONException e){
//             e.printStackTrace();
//         }
//        return JObject;
//
//    }

//    public JSONObject readJson( ){
////        super.onCreate(savedInstanceState);
//        JSONObject JArray;
//            String json = null;
//            JArray = null;
//            try {
//                InputStream is = assetManager.open("json/Numbers.json");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//
//                //파일읽기
//                String strResult = "";
//                String line = "";
//                while((line=reader.readLine()) != null){
//                    strResult += line;
//                }
//                JArray = new JSONObject(strResult);
//                return JArray;
//
//    //            AssetInputStream is;
//    //            is = (AssetInputStream) assetManager.open("Numbers.json");
//    //
//    //            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//    //            StringBuilder sb = new StringBuilder();
//    //
//    //            int bufferSize  = 1024*1024;
//    //            char readBuf[] = new char[bufferSize];
//    //            String strResult = "";
//    //            String line = "";
//    //            int resultSize = 0;
//    //            while((resultSize = reader.read(readBuf)) != -1){
//    //                if(resultSize == bufferSize){
//    //                    sb.append(readBuf);
//    //                }else{
//    //                    for(int i=0;i<resultSize; i++){
//    //                        sb.append(readBuf[i]);
//    //                    }
////    //                }
////    //            }
////                JArray = new JSONObject(json);
////    //            System.out.println(JArray);
////            } catch (IOException | JSONException e) {
////                return null;
////            }
////
////        return JArray;
//
//    }



}
