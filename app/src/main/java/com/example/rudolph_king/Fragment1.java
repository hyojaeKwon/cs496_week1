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
        actors.add(new Actor("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));
        actors.add(new Actor("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        actors.add(new Actor("Cho Yeo-jeong", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5MgWM8pkUiYkj9MEaEpO0Ir1FD9.jpg", "Cho Yeo-jeong (조여정) is a South Korean actress. Born on February 10, 1981, she began her career as a model in 1997 at the age of 16 and launched her acting career two years later. She is best known for her roles in the provocative period films “The Servant” (2010) and “The Concubine” (2012) and the television dramas “I Need Romance” (2011), “Haeundae Lovers” (2012), “Divorce Lawyer in Love” (2015) and “Perfect Wife” (2017)."));
        actors.add(new Actor("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        actors.add(new Actor("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));

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