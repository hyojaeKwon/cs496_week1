package com.example.rudolph_king.activities;

import static com.example.rudolph_king.fragments.Fragment2.refreshAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.Gift;
import com.example.rudolph_king.R;
import com.example.rudolph_king.adapters.GitfAdapter;
import com.example.rudolph_king.adapters.PhotoLargeAdapter;
import com.example.rudolph_king.fragments.Fragment3;

import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    GitfAdapter giftAdapter;
    static ArrayList<Gift> giftWishList = new ArrayList<Gift>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_row_item);

        Intent intent = getIntent();

        // setting action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("찜 목록");
        actionBar.setDisplayHomeAsUpEnabled(true);

        giftAdapter = new GitfAdapter(this, giftWishList);
        mRecyclerView = (RecyclerView) findViewById(R.id.wish_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(giftAdapter);
        mRecyclerView.smoothScrollToPosition(0);
    }

    public static void refreshWishList() {
        giftWishList.clear();
        for (int i = 0; i < Fragment3.mWishList.size(); i ++) {
            int index = Fragment3.mGiftIdList.indexOf(Fragment3.mWishList.get(i));
            Log.e("index", String.valueOf(index));
            giftWishList.add(Fragment3.mGiftList.get(index));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
