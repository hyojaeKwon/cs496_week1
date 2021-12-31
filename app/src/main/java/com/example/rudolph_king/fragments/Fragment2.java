package com.example.rudolph_king.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.Shops;
import com.example.rudolph_king.activities.JsonRead;
import com.example.rudolph_king.activities.MainActivity;
import com.example.rudolph_king.activities.PhotoActivity;
import com.example.rudolph_king.adapters.ReviewAdapter;
import com.example.rudolph_king.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment implements ReviewAdapter.OnListItemSelectedInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // gallery view
    static RecyclerView mRecyclerView;
    private static ReviewAdapter reviewAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void refreshAdapter() {
        reviewAdapter.notifyDataSetChanged();
        // mRecyclerView.setAdapter(photoAdapter);
        Log.e("refreshed", "true");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false) ;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.gallery);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        reviewAdapter = new ReviewAdapter(getContext(), MainActivity.reviewList, this);

        mRecyclerView.setAdapter(reviewAdapter);

        // 갤러리를 위한 json 파일 불러오기
        JSONObject jo = null;
        String fileName = "images.json";
        FileInputStream fis = null;
//        File file = new File(this.getActivity().getFilesDir(), fileName);
//        file.delete();
        try {
            fis = getContext().openFileInput(fileName);
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
                Log.e("First load", "false");
            }
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
            JsonRead jr = new JsonRead();
            jo = jr.reading(getContext(), "images.json");
            Log.e("First run", "true");
        }

        //reviewlist에 JSONObject 추가하기
        JSONArray ja = null;
        try {
            ja = jo.getJSONArray("Reviews");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("First hhhhhhhhhhhhhhh", ja.toString());

        for(int i = 0 ; i < ja.length() ; i++){
            JSONObject innerJSONObject = null;
            try {
                innerJSONObject = ja.getJSONObject(i);
                if (innerJSONObject != null) {
                    Log.e("First dddddddddddd", innerJSONObject.toString());
                    String jsonUriListString = innerJSONObject.getString("uriList");
                    String[] jsonReview = jsonUriListString.substring(1, jsonUriListString.lastIndexOf("]")).split(",");
                    Log.e("First sssssssssssssssss", jsonReview[0]);
                    // ArrayList jsonReview = gson.fromJson(jsonUriListString, ArrayList.class);

                    // Log.e("First sssssssssssssssss", jsonUriListString);
                    //uriList array list 생성
                    ArrayList<Uri> uriList = new ArrayList<Uri>();
                    for(int j = 0 ; j < jsonReview.length; j++){
                        Uri uri;
                        uri = Uri.parse(jsonReview[j]);
                        //찍어보는 방법
                        Log.e("First check", uri.toString());
                        uriList.add(uri);
                    }

                    GalleryImage gi = new GalleryImage();
                    gi.setUriList(uriList);
                    Log.e("First aaaaaaaaaaaaaa", String.valueOf(uriList.size()));
                    MainActivity.reviewList.add(gi);
                    refreshAdapter();
                }
            } catch (JSONException e) {
            }
        }

        // 사진 추가하기 위한 floating button 정의
        FloatingActionButton fab = view.findViewById(R.id.addPhoto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open phone gallery
                openPhoneGallery();
            }
        });

        return view;
    }

    @SuppressLint("IntentReset")
    private void openPhoneGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(intent, 101);
    }

    @Override
    public void onItemSelected(View view, int position) {
        Log.e("Listen", String.valueOf(position));
        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        intent.putExtra("pos", position);
        startActivity(intent);
    }
}