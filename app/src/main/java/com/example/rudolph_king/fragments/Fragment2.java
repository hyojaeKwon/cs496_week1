package com.example.rudolph_king.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.activities.JsonRead;
import com.example.rudolph_king.activities.MainActivity;
import com.example.rudolph_king.activities.PhotoActivity;
import com.example.rudolph_king.adapters.ReviewAdapter;
import com.example.rudolph_king.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public static void setReviewInfo(String name, String members, String date, String desc, ArrayList<Uri> uriList, Boolean newReview) {
        if (newReview) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mRecyclerView.getContext());

            View view = LayoutInflater.from(mRecyclerView.getContext())
                    .inflate(R.layout.review_info_add, null, false);
            builder.setView(view);

            final Button ButtonSubmit = (Button) view.findViewById(R.id.button_review_submit);
            final EditText editReviewName = (EditText) view.findViewById(R.id.edit_review_name);
            final EditText editReviewMembers = (EditText) view.findViewById(R.id.edit_review_members);
            final EditText editReviewDescription = (EditText) view.findViewById(R.id.edit_review_description);

            final AlertDialog dialog = builder.create();

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setView(view, 0, 0, 0, 0);
            // 삽입 버튼을 클릭했을 때
            ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // 사용자가 입력한 내용 가져오기
                    String reviewName = editReviewName.getText().toString();
                    String reviewMembers = editReviewMembers.getText().toString();
                    String reviewDescription = editReviewDescription.getText().toString();
                    reviewName = reviewName.trim();
                    reviewMembers = reviewMembers.trim();

                    if(reviewName.getBytes().length <= 0 || reviewMembers.getBytes().length <= 0){//빈값이 넘어올때의 처리
                        Toast.makeText(dialog.getContext(), "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();

                        long now = System.currentTimeMillis(); // 1970년 1월 1일부터 몇 밀리세컨드가 지났는지를 반환함
                        Date date = new Date(now);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");//형식 지정
                        String time = simpleDateFormat.format(date);

                        GalleryImage gi = new GalleryImage();
                        gi.setUriList(uriList);
                        gi.setReviewName(reviewName);
                        gi.setReviewMembers(reviewMembers);
                        gi.setReviewDescription(reviewDescription);
                        gi.setReviewDate(time);
                        MainActivity.reviewList.add(0, gi);
                        reviewAdapter.notifyItemInserted(0);
                        mRecyclerView.smoothScrollToPosition(0);
                        MainActivity.updateJSONImages(gi, -1);
                    }
                }
            });

            dialog.show();
        } else {
            GalleryImage gi = new GalleryImage();
            gi.setUriList(uriList);
            gi.setReviewName(name);
            gi.setReviewMembers(members);
            gi.setReviewDate(date);
            gi.setReviewDescription(desc);
            MainActivity.reviewList.add(0, gi);
            reviewAdapter.notifyItemInserted(0);
        }
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
        bringPrevReviews();

        // 사진 추가하기 위한 floating button 정의
        FloatingActionButton fab = view.findViewById(R.id.addPhoto);
        fab.setOnClickListener(new View.OnClickListener() {
            // floating button을 클릭했을 때
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
                // open phone gallery
                openPhoneGallery();
            }
        });

        return view;
    }

    private void bringPrevReviews() {
        // 갤러리를 위한 json 파일 불러오기
        JSONObject jo = null;
        String fileName = "images.json";
        FileInputStream fis = null;
        // 최초 설치 시 파일 초기화
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
            }
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
            JsonRead jr = new JsonRead();
            jo = jr.reading(getContext(), "images.json");
        }

        //reviewlist에 JSONObject 추가하기
        JSONArray ja = null;
        try {
            ja = jo.getJSONArray("Reviews");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0 ; i < ja.length() ; i++){
            JSONObject innerJSONObject = null;
            try {
                innerJSONObject = ja.getJSONObject(i);
                if (innerJSONObject != null) {
                    String jsonUriListString = innerJSONObject.getString("uriList");
                    String jsonNameString = innerJSONObject.getString("name");
                    String jsonMemString = innerJSONObject.getString("members");
                    String jsonDateString = innerJSONObject.getString("date");
                    String jsonDescString = innerJSONObject.getString("desc");
                    String[] jsonReview = jsonUriListString.substring(1, jsonUriListString.lastIndexOf("]")).split(", ");
                    //uriList array list 생성
                    ArrayList<Uri> uriList = new ArrayList<Uri>();
                    for(int j = 0 ; j < jsonReview.length; j++){
                        Uri uri;
                        uri = Uri.parse(jsonReview[j]);
                        //찍어보는 방법
//                        Log.e("First check", uri.toString());
                        uriList.add(uri);
                    }

                    setReviewInfo(jsonNameString, jsonMemString, jsonDateString, jsonDescString, uriList, false);
                }
            } catch (JSONException e) {
            }
        }
    }

    @SuppressLint("IntentReset")
    private void openPhoneGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(intent, 101);
    }

    // review가 클릭되었을 때
    @Override
    public void onItemSelected(View view, int position) {
        Log.e("Listen", String.valueOf(position));
        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        intent.putExtra("pos", position);
        intent.putExtra("pos_pic", 0);
        startActivity(intent);
    }
}