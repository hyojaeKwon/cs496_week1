package com.example.rudolph_king.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.rudolph_king.activities.PhotoActivity;
import com.example.rudolph_king.activities.WishListActivity;
import com.example.rudolph_king.adapters.GitfAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {
    public static ArrayList<Gift> mGiftList = null;
    private static ArrayList<Gift> mSelectedList = null;
    public static ArrayList<Integer> mGiftIdList = null;
    public static ArrayList<Integer> mWishList = null;
    private static int mSelectedListId = 0;
    private static final int mSelectedColor = 0xFFDE5F57;
    private static final int mUnselectedColor = 0xFF888888;
    int[][] mIdArr = new int[16][10];
    GitfAdapter gitfAdapter;
    RecyclerView recyclerView;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    TextView btn5;
    TextView btn6;
    TextView btn7;
    TextView btn8;
    TextView btnWishList;

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance() {
        Fragment3 fragment = new Fragment3();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mGiftList == null) {
            mGiftList = new ArrayList<Gift>();
            mGiftIdList = new ArrayList<Integer>();

            //json parse
            JsonRead jr = new JsonRead();
            JSONObject jo = jr.reading(getContext(), "products.json");
            JSONObject innerJo = null;
            JSONArray innerJa = null;
            try {
                innerJo = jo.getJSONObject("Products");
                innerJa = innerJo.getJSONArray("productList");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i = 0 ; i <innerJa.length() ; i++){
                JSONObject innerJsonObj = null;
                try {
                    innerJsonObj = innerJa.getJSONObject(i);
                    Gift g = new Gift(innerJsonObj);
                    mGiftList.add(g);
                    mGiftIdList.add(g.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ArrayList<JSONObject> joList = new ArrayList<JSONObject>();

            try {
                joList.add(innerJo.getJSONObject("all"));
                joList.add(innerJo.getJSONObject("girls"));
                joList.add(innerJo.getJSONObject("boys"));
                joList.add(innerJo.getJSONObject("students"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //parse Id
            try {
                for (int j = 0; j < joList.size(); j++) {
                    JSONObject jojo = joList.get(j);
                    JSONArray allJoArr1 = jojo.getJSONArray("list0to1");
                    mIdArr[j * 4 + 0] = parseId(allJoArr1);
                    JSONArray allJoArr2 = jojo.getJSONArray("list1to3");
                    mIdArr[j * 4 + 1] = parseId(allJoArr2);
                    JSONArray allJoArr3 = jojo.getJSONArray("list3to5");
                    mIdArr[j * 4 + 2] = parseId(allJoArr3);
                    JSONArray allJoArr4 = jojo.getJSONArray("list5over");
                    mIdArr[j * 4 + 3] = parseId(allJoArr4);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (mSelectedList == null) {
            mSelectedList = new ArrayList<Gift>();
        }
        if (mWishList == null) {
            mWishList = new ArrayList<Integer>();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.gift_layout);
        btn1 = (Button) view.findViewById(R.id.imageButton_all);
        btn2 = (Button) view.findViewById(R.id.imageButton_girls);
        btn3 = (Button) view.findViewById(R.id.imageButton_boys);
        btn4 = (Button) view.findViewById(R.id.imageButton_students);

        btn5 =  (TextView) view.findViewById(R.id.btn5);
        btn6 =  (TextView) view.findViewById(R.id.btn6);
        btn7 =  (TextView) view.findViewById(R.id.btn7);
        btn8 =  (TextView) view.findViewById(R.id.btn8);

        btnWishList = (TextView) view.findViewById(R.id.wishList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        gitfAdapter = new GitfAdapter(getContext(),mSelectedList);
        recyclerView.setAdapter(gitfAdapter);

        setStatus(0);
        setStatus(4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus(0);
                setStatus(4);
                mSelectedListId = 0;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus(1);
                setStatus(4);
                mSelectedListId = 4;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus(2);
                setStatus(4);
                mSelectedListId = 8;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus(3);
                setStatus(4);
                mSelectedListId = 12;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(4);
                mSelectedListId = getStatus() * 4;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(5);
                mSelectedListId = getStatus() * 4 + 1;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(6);
                mSelectedListId = getStatus() * 4 + 2;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(7);
                mSelectedListId = getStatus() * 4 + 3;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btnWishList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                WishListActivity.refreshWishList();
                Intent intent = new Intent(getContext(), WishListActivity.class);
                getContext().startActivity(intent);
            }
        });

        gitfAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                recyclerView.setAdapter(gitfAdapter);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        setStatus(0);
        setStatus(4);
        mSelectedListId = 0;
        changeList(mSelectedListId);
        gitfAdapter.filterList(mSelectedList);
        super.onResume();
    }

    private void changeList(int listId) {
        for(int i = 0 ; i < mIdArr[listId].length; i++){
            int id = mIdArr[listId][i];
            if (i > mSelectedList.size() - 1) {
                mSelectedList.add(mGiftList.get(mGiftIdList.indexOf(id)));
            } else {
                mSelectedList.set(i, mGiftList.get(mGiftIdList.indexOf(id)));
            }
        }
    }

    private int[] parseId(JSONArray ja) throws JSONException {
        int[] newInt = new int[10];
        for(int i = 0 ; i < ja.length() ; i ++ ){
            String now = ja.getString(i);
            newInt[i] = Integer.parseInt(now);
        }
        return newInt;
    }

    //상태 반환
    private int getStatus(){
        if(btn1.isSelected() == true){
            return 0;
        }else if(btn2.isSelected() == true){
            return 1;
        } else if(btn3.isSelected() == true){
            return 2;
        } else if(btn4.isSelected() == true){
            return 3;
        } else {
            return 5;
        }
    }

    private void setStatus(int status) {
        recyclerView.smoothScrollToPosition(0);
        switch (status) {
            case 0:
                btn1.setSelected(true);
                btn2.setSelected(false);
                btn3.setSelected(false);
                btn4.setSelected(false);
                break;
            case 1:
                btn1.setSelected(false);
                btn2.setSelected(true);
                btn3.setSelected(false);
                btn4.setSelected(false);
                break;
            case 2:
                btn1.setSelected(false);
                btn2.setSelected(false);
                btn3.setSelected(true);
                btn4.setSelected(false);
                break;
            case 3:
                btn1.setSelected(false);
                btn2.setSelected(false);
                btn3.setSelected(false);
                btn4.setSelected(true);
                break;
            case 4:
                btn5.setSelected(true);
                btn6.setSelected(false);
                btn7.setSelected(false);
                btn8.setSelected(false);
                break;
            case 5:
                btn5.setSelected(false);
                btn6.setSelected(true);
                btn7.setSelected(false);
                btn8.setSelected(false);
                break;
            case 6:
                btn5.setSelected(false);
                btn6.setSelected(false);
                btn7.setSelected(true);
                btn8.setSelected(false);
                break;
            case 7:
                btn5.setSelected(false);
                btn6.setSelected(false);
                btn7.setSelected(false);
                btn8.setSelected(true);
                break;
            default:
                break;
        }
    }

}