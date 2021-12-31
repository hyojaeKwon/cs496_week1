package com.example.rudolph_king.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rudolph_king.R;
import com.example.rudolph_king.activities.MainActivity;
import com.example.rudolph_king.activities.PhotoActivity;
import com.example.rudolph_king.adapters.ReviewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2_past#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2_past extends Fragment implements ReviewAdapter.OnListItemSelectedInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // gallery view
    static RecyclerView mRecyclerView;
    private static ReviewAdapter photoAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2_past() {
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
    public static Fragment2_past newInstance(String param1, String param2) {
        Fragment2_past fragment = new Fragment2_past();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void refreshAdapter() {
        photoAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(photoAdapter);
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
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        photoAdapter = new ReviewAdapter(getContext(), MainActivity.reviewList, this);

        mRecyclerView.setAdapter(photoAdapter);

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