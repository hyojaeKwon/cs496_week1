package com.example.rudolph_king.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.activities.JsonRead;
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
    private static ArrayList<Integer> mGiftIdList = null;
    public static ArrayList<Integer> mWishList = null;
    private static int mSelectedListId = 0;
    int[][] mIdArr = new int[16][10];
    GitfAdapter gitfAdapter;
    RecyclerView recyclerView;
    private static boolean btn1Status  = true;
    private static boolean btn2Status = false;
    private static boolean btn3Status = false;
    private static boolean btn4Status = false;

    private static boolean btn5Status  = true;
    private static boolean btn6Status = false;
    private static boolean btn7Status = false;
    private static boolean btn8Status = false;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.gift_layout);
        ImageButton btn1 = (ImageButton) view.findViewById(R.id.imageButton_all);
        ImageButton btn2 = (ImageButton) view.findViewById(R.id.imageButton_girls);
        ImageButton btn3 = (ImageButton) view.findViewById(R.id.imageButton_boys);
        ImageButton btn4 = (ImageButton) view.findViewById(R.id.imageButton_students);

        TextView btn5 =  (TextView) view.findViewById(R.id.btn5);
        btn5.setTextColor(Color.BLUE);
        TextView btn6 =  (TextView) view.findViewById(R.id.btn6);
        TextView btn7 =  (TextView) view.findViewById(R.id.btn7);
        TextView btn8 =  (TextView) view.findViewById(R.id.btn8);

        changeList(mSelectedListId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        gitfAdapter = new GitfAdapter(getContext(),mSelectedList);
        recyclerView.setAdapter(gitfAdapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus(0);
                setStatus(4);

                mSelectedListId = 0;
                btn5.setTextColor(Color.BLUE);
                btn6.setTextColor(Color.GRAY);
                btn7.setTextColor(Color.GRAY);
                btn8.setTextColor(Color.GRAY);
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
                btn5.setTextColor(Color.BLUE);
                btn6.setTextColor(Color.GRAY);
                btn7.setTextColor(Color.GRAY);
                btn8.setTextColor(Color.GRAY);
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
                btn5.setTextColor(Color.BLUE);
                btn6.setTextColor(Color.GRAY);
                btn7.setTextColor(Color.GRAY);
                btn8.setTextColor(Color.GRAY);
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
                btn5.setTextColor(Color.BLUE);
                btn6.setTextColor(Color.GRAY);
                btn7.setTextColor(Color.GRAY);
                btn8.setTextColor(Color.GRAY);
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(4);
                btn5.setTextColor(Color.BLUE);
                btn6.setTextColor(Color.GRAY);
                btn7.setTextColor(Color.GRAY);
                btn8.setTextColor(Color.GRAY);

                mSelectedListId = getStatus() * 4;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(5);
                btn5.setTextColor(Color.GRAY);
                btn6.setTextColor(Color.BLUE);
                btn7.setTextColor(Color.GRAY);
                btn8.setTextColor(Color.GRAY);
                mSelectedListId = getStatus() * 4 + 1;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(6);
                btn5.setTextColor(Color.GRAY);
                btn6.setTextColor(Color.GRAY);
                btn7.setTextColor(Color.BLUE);
                btn8.setTextColor(Color.GRAY);
                mSelectedListId = getStatus() * 4 + 2;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setStatus(7);
                btn5.setTextColor(Color.GRAY);
                btn6.setTextColor(Color.GRAY);
                btn7.setTextColor(Color.GRAY);
                btn8.setTextColor(Color.BLUE);
                mSelectedListId = getStatus() * 4 + 3;
                changeList(mSelectedListId);
                gitfAdapter.filterList(mSelectedList);
            }
        });
        return view;
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
    private void setStatus(int status) {
        recyclerView.smoothScrollToPosition(0);
        switch (status) {
            case 0:
                btn1Status= true;
                btn2Status= false;
                btn3Status= false;
                btn4Status= false;
                break;
            case 1:
                btn1Status= false;
                btn2Status= true;
                btn3Status= false;
                btn4Status= false;
                break;
            case 2:
                btn1Status= false;
                btn2Status= false;
                btn3Status= true;
                btn4Status= false;
                break;
            case 3:
                btn1Status= false;
                btn2Status= false;
                btn3Status= false;
                btn4Status= true;
                break;
//            case 4:
//                btn5Status= true;
//                btn6Status= false;
//                btn7Status= false;
//                btn8Status= false;
//                break;
//            case 5:
//                btn5Status= false;
//                btn6Status= true;
//                btn7Status= false;
//                btn8Status= false;
//                break;
//            case 6:
//                btn5Status= false;
//                btn6Status= false;
//                btn7Status= true;
//                btn8Status= false;
//                break;
//            case 7:
//                btn5Status= false;
//                btn6Status= false;
//                btn7Status= false;
//                btn8Status= true;
//                break;
        }
    }

}