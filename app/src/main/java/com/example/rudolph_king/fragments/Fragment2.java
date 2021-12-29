package com.example.rudolph_king.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rudolph_king.activities.MainActivity;
import com.example.rudolph_king.activities.PhotoActivity;
import com.example.rudolph_king.adapters.PhotoAdapter;
import com.example.rudolph_king.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment implements PhotoAdapter.OnListItemSelectedInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // gallery view
    public ArrayList<String> photoList;
    RecyclerView mRecyclerView;
    private static PhotoAdapter photoAdapter;

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
        View view = inflater.inflate(R.layout.fragment_2, container, false) ;

        // temp list
        photoList = new ArrayList<String>();
        photoList.add("https://www.artinsight.co.kr/data/tmp/1907/1569b10b9feab87cfaffe39b5cdf065b_VswpdlGWkOdSD9IrCKPSRMk4cY.jpg");
        photoList.add("https://media.bunjang.co.kr/product/118726183_1_1583395080_w360.jpg");
        photoList.add("https://lh3.googleusercontent.com/proxy/eI1eRee5msOEqu8YFU0S07ZN0IAuZA3cns0Yxq5AemcUxdbQuwbVxpzwRb6LDjfcILRUIoxmNgT6AMqWpiGjSfdkgSebYDJ8p0B4ClupU-sRfP9NcQ46UoGX");
        photoList.add("https://cdn.ggumim.co.kr/cache/star/600/20210317170629wybexPof13.jpeg");
        photoList.add("https://image.ohou.se/i/bucketplace-v2-development/uploads/cards/snapshots/1578977794_sNqqt.jpeg?gif=1&w=480&h=480&c=c&q=80");
        photoList.add("https://pbs.twimg.com/media/EZudW4NUwAAcSHA.jpg");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.gallery);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        photoAdapter = new PhotoAdapter(getContext(), photoList, this);
        mRecyclerView.setAdapter(photoAdapter);

        return view;
    }

    @Override
    public void onItemSelected(View view, int position) {
//        PhotoAdapter.ViewHolder viewHolder = (PhotoAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        String image = photoList.get(position);
        Log.e("Listen", String.valueOf(position));
        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        intent.putExtra("pos", position);
        intent.putExtra("img", image);
        startActivity(intent);
    }
}