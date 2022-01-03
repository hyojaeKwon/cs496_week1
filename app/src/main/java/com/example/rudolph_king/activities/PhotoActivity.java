package com.example.rudolph_king.activities;

import static com.example.rudolph_king.fragments.Fragment2.refreshAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.rudolph_king.GalleryImage;
import com.example.rudolph_king.R;
import com.example.rudolph_king.adapters.PhotoLargeAdapter;

public class PhotoActivity extends AppCompatActivity {// implements PhotoLargeAdapter.OnListItemSelectedInterface {
    RecyclerView mRecyclerView;
    CardView mReviewInfo;
    CardView mReviewInfoEdit;
    TextView mReviewMembers;
    TextView mReviewDate;
    TextView mReviewDescription;
    EditText mReviewMembersEdit;
    EditText mReviewDescriptionEdit;
    PhotoLargeAdapter photoLargeAdapter;
    boolean onEditMode;
    private int position;
    private int position_pic;
    private InputMethodManager imm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoactivity_main);


        Intent intent = getIntent();
        position = intent.getIntExtra("pos", 0);
        position_pic = intent.getIntExtra("pos_pic", 0);
        GalleryImage review = MainActivity.reviewList.get(position);

        // setting action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(review.getReviewName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        onEditMode = false;

        photoLargeAdapter = new PhotoLargeAdapter(this, review.getUriList(), position); //this, position);
        mReviewInfo = (CardView) findViewById(R.id.cardView);
        mReviewInfoEdit = (CardView) findViewById(R.id.cardView_edit);
        mRecyclerView = (RecyclerView) findViewById(R.id.photo_list);
        mReviewMembers = (TextView) findViewById(R.id.photo_members);
        mReviewDate = (TextView) findViewById(R.id.photo_date);
        mReviewDescription = (TextView) findViewById(R.id.photo_description);
        mReviewMembersEdit = (EditText) findViewById(R.id.photo_members_edit);
        mReviewDescriptionEdit = (EditText) findViewById(R.id.photo_description_edit);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(photoLargeAdapter);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.smoothScrollToPosition(position_pic);

        mReviewMembers.setText(review.getReviewMembers());
        mReviewDate.setText(review.getReviewDate());
        mReviewDescription.setText(review.getReviewDescription());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_edit:
                if (onEditMode) {
                    item.setIcon(R.drawable.ic_edit_24);
                    mReviewInfoEdit.setVisibility(View.GONE);

                    String reviewMembers = mReviewMembersEdit.getText().toString();
                    String reviewDescription = mReviewDescriptionEdit.getText().toString();
                    mReviewMembers.setText(reviewMembers);
                    mReviewDescription.setText(reviewDescription);
                    mReviewInfo.setVisibility(View.VISIBLE);

                    GalleryImage gi = MainActivity.reviewList.get(position);
                    gi.setReviewMembers(reviewMembers);
                    gi.setReviewDescription(reviewDescription);
                    MainActivity.reviewList.set(position, gi);
                    refreshAdapter();
                    MainActivity.updateJSONImages(gi, position);
                    imm.hideSoftInputFromWindow(findViewById(R.id.cardView).getWindowToken(), 0);
                    onEditMode = false;
                } else {
                    item.setIcon(R.drawable.ic_check_24);
                    mReviewInfo.setVisibility(View.GONE);
                    mReviewInfoEdit.setVisibility(View.VISIBLE);

                    mReviewMembersEdit.setText(mReviewMembers.getText());
                    mReviewDescriptionEdit.setText(mReviewDescription.getText());
                    onEditMode = true;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onItemSelected(View view, int position, int position_pic) {
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu) ;

        return true ;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }


}
