package com.example.rudolph_king;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout listContainer;

    // customizing listView
    ArrayList<Shops> shopList;
    RecyclerView mRecyclerView;
    private static CustomAdapter customAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }




    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    public void textView(String a){
//        TextView view1 = new TextView(getContext());
//        view1.setText(a);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.gravity= Gravity.CENTER;
//        lp.leftMargin=20;
//        view1.setLayoutParams(lp);
//
//        listContainer.addView(view1);
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        setContentView(R.layout.activity_main);
//        listContainer = listContainer.findViewById(R.id.listView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false) ;


        // add list elements -- test
        shopList = new ArrayList<>();
        JsonRead jr = new JsonRead();
        JSONObject jo = jr.reading(getContext());
        JSONArray ja = null;
        try {
            ja = jo.getJSONArray("Numbers");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i < ja.length() ; i++){
            JSONObject innerJSONObject = null;
            try {
                innerJSONObject = ja.getJSONObject(i);
            } catch (JSONException e) {
                innerJSONObject = null;

            }
            try {
                shopList.add(new Shops(innerJSONObject));
            } catch (JSONException e) {
                continue;
            }

        }

;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        customAdapter = new CustomAdapter(getContext(),shopList);
        mRecyclerView.setAdapter(customAdapter);

        return view;
    }


}