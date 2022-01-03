package com.example.rudolph_king.fragments;

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
    ArrayList<Gift> mGiftList = null;
    int[][] mIdArr = new int[16][10];
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
                    mGiftList.add(new Gift(innerJsonObj));
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
                    JSONArray allJoArr3 = jojo.getJSONArray("list0to1");
                    mIdArr[j * 4 + 2] = parseId(allJoArr3);
                    JSONArray allJoArr4 = jojo.getJSONArray("list3to5");
                    mIdArr[j * 4 + 3] = parseId(allJoArr4);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) view.findViewById(R.id.gift_layout);
        ImageButton btn1 = (ImageButton) view.findViewById(R.id.imageButton_all);
        ImageButton btn2 = (ImageButton) view.findViewById(R.id.imageButton_girls);
        ImageButton btn3 = (ImageButton) view.findViewById(R.id.imageButton_boys);
        ImageButton btn4 = (ImageButton) view.findViewById(R.id.imageButton_students);

        TextView btn5 =  (TextView) view.findViewById(R.id.btn5);
        TextView btn6 =  (TextView) view.findViewById(R.id.btn6);
        TextView btn7 =  (TextView) view.findViewById(R.id.btn7);
        TextView btn8 =  (TextView) view.findViewById(R.id.btn8);

        ArrayList<Gift> changedList = new ArrayList<>();
        changedList = isMatch(nowStatus, mGiftList, mIdArr);

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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
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
                changedList = isMatch(nowStatus,mGiftList,mIdArr);
                gitfAdapter.filterList(changedList);
            }
        });
        return view;
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