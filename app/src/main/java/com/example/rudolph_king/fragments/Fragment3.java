package com.example.rudolph_king.fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.activities.JsonRead;
import com.example.rudolph_king.adapters.GitfAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    ArrayList<Gift> giftArrayList;
    GitfAdapter gitfAdapter;
    private static int nowStatus = 1;
    private static boolean btn1Status  = true;
    private static boolean btn2Status = false;
    private static boolean btn3Status = false;
    private static boolean btn4Status = false;

    private static boolean btn5Status  = true;
    private static boolean btn6Status = false;
    private static boolean btn7Status = false;
    private static boolean btn8Status = false;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }


    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        giftArrayList = new ArrayList<>();
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_3, container, false);
         RecyclerView recyclerView;
         recyclerView = (RecyclerView) view.findViewById(R.id.gift_layout);
        ImageButton btn1 = (ImageButton) view.findViewById(R.id.imageButton6);
        ImageButton btn2 = (ImageButton) view.findViewById(R.id.imageButton7);
        ImageButton btn3 = (ImageButton) view.findViewById(R.id.imageButton8);
        ImageButton btn4 = (ImageButton) view.findViewById(R.id.imageButton9);

        TextView btn5 =  (TextView) view.findViewById(R.id.btn5);
        TextView btn6 =  (TextView) view.findViewById(R.id.btn6);
        TextView btn7 =  (TextView) view.findViewById(R.id.btn7);
        TextView btn8 =  (TextView) view.findViewById(R.id.btn8);



         //json parse
        String jsonStr = getJsonString();
        JSONObject jo = null;
        try {
            jo = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject innerJo = null;
        try {
            innerJo = jo.getJSONObject("Products");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray innerJa = null;
        try {
            innerJa = innerJo.getJSONArray("productList");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0 ; i <innerJa.length() ; i++){
            JSONObject innerJsonObj = null;
            try {
                innerJsonObj = innerJa.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                giftArrayList.add(new Gift(innerJsonObj));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        JSONObject allJo = null;
        JSONObject girlsJo = null;
        JSONObject boysJo =null;
        JSONObject studentsJo =null;
        int[][] idArr = new int[16][10];

        try {
            allJo = innerJo.getJSONObject("all");
            girlsJo = innerJo.getJSONObject("girls");
            boysJo = innerJo.getJSONObject("boys");
            studentsJo = innerJo.getJSONObject("students");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //parse Id
        try {
            JSONArray allJoArr1 = allJo.getJSONArray("list0to1");
            idArr[0] = parseId(allJoArr1);
            JSONArray allJoArr2 = allJo.getJSONArray("list1to3");
            idArr[1] = parseId(allJoArr2);
            JSONArray allJoArr3 = allJo.getJSONArray("list0to1");
            idArr[2] = parseId(allJoArr3);
            JSONArray allJoArr4 = allJo.getJSONArray("list3to5");
            idArr[3] = parseId(allJoArr4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray allJoArr1 = girlsJo.getJSONArray("list0to1");
            idArr[4] = parseId(allJoArr1);
            JSONArray allJoArr2 = girlsJo.getJSONArray("list1to3");
            idArr[5] = parseId(allJoArr2);
            JSONArray allJoArr3 = girlsJo.getJSONArray("list0to1");
            idArr[6] = parseId(allJoArr3);
            JSONArray allJoArr4 = girlsJo.getJSONArray("list3to5");
            idArr[7] = parseId(allJoArr4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray allJoArr1 = boysJo.getJSONArray("list0to1");
            idArr[8] = parseId(allJoArr1);
            JSONArray allJoArr2 = boysJo.getJSONArray("list1to3");
            idArr[9] = parseId(allJoArr2);
            JSONArray allJoArr3 = boysJo.getJSONArray("list0to1");
            idArr[10] = parseId(allJoArr3);
            JSONArray allJoArr4 = boysJo.getJSONArray("list3to5");
            idArr[11] = parseId(allJoArr4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray allJoArr1 = studentsJo.getJSONArray("list0to1");
            idArr[12] = parseId(allJoArr1);
            JSONArray allJoArr2 = studentsJo.getJSONArray("list1to3");
            idArr[13] = parseId(allJoArr2);
            JSONArray allJoArr3 = studentsJo.getJSONArray("list0to1");
            idArr[14] = parseId(allJoArr3);
            JSONArray allJoArr4 = studentsJo.getJSONArray("list3to5");
            idArr[15] = parseId(allJoArr4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Gift> changedList = new ArrayList<>();
        changedList = isMatch(nowStatus,giftArrayList,idArr);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        gitfAdapter = new GitfAdapter(getContext(),changedList);
        recyclerView.setAdapter(gitfAdapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn1Status = true;
                btn2Status = false;
                btn3Status = false;
                btn4Status = false;
                nowStatus = 1;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn1Status = false;
                btn2Status = true;
                btn3Status = false;
                btn4Status = false;
                nowStatus = 5;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn1Status = false;
                btn2Status = false;
                btn3Status = true;
                btn4Status = false;
                nowStatus = 9;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn1Status = false;
                btn2Status = false;
                btn3Status = false;
                btn4Status = true;
                nowStatus = 13;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btn5Status= true;
                btn6Status= false;
                btn7Status= false;
                btn8Status= false;
                nowStatus = getStatus() * 4 + 1;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btn5Status= false;
                btn6Status= true;
                btn7Status= false;
                btn8Status= false;
                nowStatus = getStatus() * 4 + 2;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btn5Status= false;
                btn6Status= false;
                btn7Status= true;
                btn8Status= false;
                nowStatus = getStatus() * 4 + 3;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btn5Status= false;
                btn6Status= false;
                btn7Status= false;
                btn8Status= true;
                nowStatus = getStatus() * 4 + 4;
                ArrayList<Gift> changedList = new ArrayList<>();
                changedList = isMatch(nowStatus,giftArrayList,idArr);
                gitfAdapter.filterList(changedList);
            }
        });
        return view;
    }

    private String getJsonString(){
//        String json = "";
        String strResult = "";
        AssetManager assetManager = getContext().getResources().getAssets();
        try {
            InputStream is = assetManager.open("products.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";

            while((line=reader.readLine()) != null){
                strResult += line;
            }

//            json = new String(strResult,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("errorJson","hi");
        }

        return strResult;
    }


    private ArrayList<Gift> isMatch(int status,ArrayList<Gift>currentArray, int[][] idArr){
        ArrayList<Gift> newArr = new ArrayList<>();
        for(int i = 0 ; i < currentArray.size() ; i++){
            for(int j = 0 ; j < idArr[status-1].length ; j ++){
                if (currentArray.get(i).getId() == idArr[status-1][j]){
                    newArr.add(currentArray.get(i));
                }
            }
        }
        return newArr;
    }

    private int[] parseId(JSONArray ja) throws JSONException {
        int[] newInt = new int[10];
        for(int i = 0 ; i<ja.length() ; i ++ ){
            String now = ja.getString(i);
            newInt[i] = Integer.parseInt(now);
        }
        return newInt;
    }
    private int getStatus(){
        if(btn1Status == true){
            return 0;
        }else if(btn2Status == true){
            return 1;
        } else if(btn3Status == true){
            return 2;
        } else if(btn4Status == true){
            return 3;
        } else {
            return 5;
        }
    }
}