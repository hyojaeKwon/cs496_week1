package com.example.rudolph_king.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.activities.MainActivity;
import com.example.rudolph_king.activities.PhotoActivity;
import com.example.rudolph_king.adapters.PhotoAdapter;
import com.example.rudolph_king.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
    public ArrayList<Uri> photoList;
    static RecyclerView mRecyclerView;
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

        // temp list
        photoList = new ArrayList<Uri>();

        // bring images from storage
        try {
            // String imgpath = getCacheDir() + "/" + photoList.size();   // 내부 저장소에 저장되어 있는 이미지 경로
            // photoList.add(imgpath);

        } catch (Exception e) {

        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.gallery);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        photoAdapter = new PhotoAdapter(getContext(), photoList, this);
        refreshAdapter();

        FloatingActionButton fab = view.findViewById(R.id.addPhoto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Photo", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

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
//        PhotoAdapter.ViewHolder viewHolder = (PhotoAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        Uri image = photoList.get(position);
        Log.e("Listen", String.valueOf(position));
        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        intent.putExtra("pos", position);
        intent.putExtra("img", image.getPath());
        startActivity(intent);
    }
}