package com.example.rudolph_king;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    public VPAdapter(@NonNull FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new Fragment1());
        items.add(new Fragment2());
        items.add(new Fragment3());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
