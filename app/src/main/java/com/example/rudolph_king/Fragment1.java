package com.example.rudolph_king;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


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


    // customizing listView
    ArrayList<Actor> actors;
    RecyclerView mRecyclerView;
    private static CustomAdapter customAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters

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


        // add list elements -- test
        actors = new ArrayList<>();
        actors.add(new Actor("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "010-0000-0000"));
        actors.add(new Actor("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "010-1111-1111"));
        actors.add(new Actor("Cho Yeo-jeong", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5MgWM8pkUiYkj9MEaEpO0Ir1FD9.jpg", "010-2222-2222"));
        actors.add(new Actor("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "010-3333-3333"));
        actors.add(new Actor("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "010-4444-4444"));
        actors.add(new Actor("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "010-5555-5555"));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        customAdapter = new CustomAdapter(getContext(),actors);
        mRecyclerView.setAdapter(customAdapter);
//        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
//                //각 아이템을 분간 할 수 있는 position과 뷰
//                String selectedItem = (String) view.findViewById(R.id.textView_name).getTag().toString();
//                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

    //data class
    class Actor {
        private String name;
        private String summary;
        private String thumb_url;

        public Actor(String name, String thumb_url, String summary) {
            this.name = name;
            this.summary = summary;
            this.thumb_url = thumb_url;
        }

        public String getName() {
            return name;
        }

        public String getSummary() {
            return summary;
        }

        public String getThumb_url() {
            return thumb_url;
        }
    }
}