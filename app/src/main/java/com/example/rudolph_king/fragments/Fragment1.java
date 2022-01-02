package com.example.rudolph_king.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.rudolph_king.adapters.CustomAdapter;
import com.example.rudolph_king.activities.JsonRead;
import com.example.rudolph_king.R;
import com.example.rudolph_king.Shops;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Fragment1 extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private InputMethodManager imm;

    // customizing listView
    static ArrayList<Shops> shopList;
    static ArrayList<Shops> filteredList;


     public boolean isFilteredListEmpty(){
         if (this.filteredList == null){
             return true;
         }
         else{
             if (this.filteredList.isEmpty()){
                 return true;
             }else{
                 return false;
             }
         }
     }
    RecyclerView mRecyclerView;
    private static CustomAdapter customAdapter;
    EditText searchET;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false) ;
        imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);


        // add list elements -- test
        shopList = new ArrayList<>();
        filteredList = new ArrayList<>();

        //json파일 parse 하는 부분
        JsonRead jr = new JsonRead();
        JSONObject jo = jr.reading(getContext(), "shop.json");
        JSONArray ja = null;
        try {
            ja = jo.getJSONArray("Numbers");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //shoplist에 JSONObject 추가하기
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
        //영업 상태에 따라 정렬하기
        Collections.sort(shopList,new SortIsOpen());
        imm.hideSoftInputFromWindow(view.findViewById(R.id.searchCardView).getWindowToken(), 0);

        //view에서 text들어가는 부분 찾는 부분
        searchET = view.findViewById(R.id.searchFood);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        customAdapter = new CustomAdapter(getContext(),shopList);
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
        filteredList.clear();

        //검색 결과 찾는 부분
        for (int i = 0 ; i < shopList.size() ; i++){
//            Log.e("nowList", Integer.toString(shopList.size()));
            if(String.valueOf(shopList.get(i).getT()).contains(searchText)){
                filteredList.add(shopList.get(i));
            }
        }
        //필터링된 메서드 cA에 다시 전달
        customAdapter.filterList(filteredList);
    }

    public ArrayList<Shops> getShopList(){
        Log.e("nowList", Integer.toString(shopList.size()));
        return shopList;
    }
    public ArrayList<Shops> getFilteredList(){
        return filteredList;
    }

    public Shops getInfo(int pos){
        if(filteredList == null){
            return (Shops)(shopList.get(pos));
        } else {
            return (Shops)(filteredList.get(pos));
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