package com.example.rudolph_king.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rudolph_king.R;
import com.example.rudolph_king.Shops;
import com.example.rudolph_king.activities.JsonRead;
import com.example.rudolph_king.adapters.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Fragment1 extends Fragment{
    // fields
    private static ArrayList<Shops> mShopList = null;
    private static ArrayList<Shops> mFilteredList = null;
    private InputMethodManager imm;
    private static RecyclerView mRecyclerView;
    private static CustomAdapter customAdapter;
    private static EditText searchET;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 변수 초기화
        if (mShopList == null) {
            mShopList = new ArrayList<Shops>();

            // shop.json에서 parsing을 통해 데이터 가져오기
            JsonRead jr = new JsonRead();
            JSONObject jo = jr.reading(getContext(), "shop.json");
            JSONArray ja = null;
            try {
                ja = jo.getJSONArray("Numbers");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //shoplist에 JSONObject 추가하기
            for(int i = 0 ; i < ja.length() ; i++) {
                JSONObject innerJSONObject = null;
                try {
                    innerJSONObject = ja.getJSONObject(i);
                } catch (JSONException e) {
                    innerJSONObject = null;
                }
                try {
                    mShopList.add(new Shops(innerJSONObject));
                } catch (JSONException e) {
                    continue;
                }
            }
            //영업 상태에 따라 정렬하기
            Collections.sort(mShopList,new SortIsOpen());
        }
        if (mFilteredList == null) {
            mFilteredList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false) ;
        imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.findViewById(R.id.searchCardView).getWindowToken(), 0);

        mFilteredList.clear();

        //view에서 text들어가는 부분 찾는 부분
        searchET = view.findViewById(R.id.searchFood);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        customAdapter = new CustomAdapter(getContext(),mShopList);
        mRecyclerView.setAdapter(customAdapter);

         //textChangeListener part
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //입력한 문자 찾아서 검색  메서드에 전달
                String searchText = searchET.getText().toString();
                searchFilter(searchText);
            }
        });
        return view;
    }

    //검색 method
    public void searchFilter(String searchText){
        mFilteredList.clear();

        //검색 결과 찾는 부분
        for (int i = 0 ; i < mShopList.size() ; i++){
//            Log.e("nowList", Integer.toString(shopList.size()));
            if(String.valueOf(mShopList.get(i).getT()).contains(searchText)){
                mFilteredList.add(mShopList.get(i));
            }
        }
        //필터링된 메서드 cA에 다시 전달
        customAdapter.filterList(mFilteredList);
    }

    public ArrayList<Shops> getShopList(){
        // Log.e("nowList", Integer.toString(mShopList.size()));
        return mShopList;
    }
    public ArrayList<Shops> getFilteredList(){
        return mFilteredList;
    }

    public Shops getInfo(int pos){
        if(mFilteredList == null || mFilteredList.size() == 0 ){
            return (Shops)(mShopList.get(pos));
        } else {
            Log.e("filteredList",Integer.toString(mFilteredList.size()));
            return (Shops)(mFilteredList.get(pos));
        }
    }

    public boolean isFilteredListEmpty(){
        if (this.mFilteredList == null){
            return true;
        }
        else{
            if (this.mFilteredList.isEmpty()){
                return true;
            }else{
                return false;
            }
        }
    }
}

//Comparator 구현
class SortIsOpen implements Comparator<Shops>{
    @Override
    public int compare(Shops s1,Shops s2){
        int s1_open = s1.getIsOpen() ? 1: 0;
        int s2_open = s2.getIsOpen() ? 1: 0;
        if (s1_open > s2_open){
            return -1;
        }
        else if(s1_open < s2_open) {
            return 1;
        }
        else{
            return 0;
        }
    }
}