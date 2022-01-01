package com.example.rudolph_king.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.rudolph_king.Shops;
import com.example.rudolph_king.fragments.Fragment1;

import com.example.rudolph_king.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CallActivity extends AppCompatActivity {

    public TextView tv_isOpen;
    public TextView tv_name;
    public LinearLayout ll_tag;
    public TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        setItems();

        Button button = findViewById(R.id.button_call);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //context 생성
                Context c = view.getContext();
                Intent intent = new Intent(Intent.ACTION_CALL);
                //번호
                String phone= getNum();
                phone = "tel:"+ phone;
                intent.setData(Uri.parse(phone));

                try {
                    c.startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getShopTitle());
    }

    //지도 띄우는 코드
    private void initView(Shops shop){
        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        //지도 추가하는 코드
        MapPOIItem marker = new MapPOIItem();
        //가게 위치를 point로 저장
        MapPoint mp = MapPoint.mapPointWithGeoCoord(shop.getLatitude(), shop.getLongitude());
        //가게의 위치를 지도에 추가하는 코드
        mapView.setMapCenterPoint(mp, true);
        mapView.setZoomLevel(4,true);

        //줌인 줌아웃 허용
        mapView.zoomOut(true);
        mapView.zoomIn(true);

        //마커 생성 코드
        marker.setItemName(shop.getT());
        marker.setTag(0);

        //마커 모양 설정
        marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);

        //마커 부착
        marker.setMapPoint(mp);
        mapView.addPOIItem(marker);

        mapViewContainer.addView(mapView);
    }


    Intent intent = new Intent();
    String name = "pos";
    private int pos = intent.getIntExtra(name,0);
    Fragment1 f1  = new Fragment1();
    private boolean chooseOriginal = f1.isFilteredListEmpty();

    @SuppressLint("ResourceAsColor")
    public void setItems(){

        Shops shopNow = f1.getInfo(pos);

        this.ll_tag = (LinearLayout) findViewById(R.id.tag_linear);
        this.tv_isOpen = (TextView) findViewById(R.id.call_isOpen);
        this.tv_name = (TextView) findViewById(R.id.title_of_shop_detail);
        this.tv_phone = (TextView) findViewById(R.id.call_phone);

        if (shopNow.getIsOpen() == true){
            tv_isOpen.setText("영업 중");
            tv_isOpen.setTextColor(R.color.open);
        } else {
            tv_isOpen.setText("영업 종료");
            tv_isOpen.setTextColor(R.color.not_open);
        }
        tv_name.setText(shopNow.getT());
        tv_phone.setText(shopNow.getPhone());

        initView(shopNow);

        for(int i= 0 ; i < shopNow.getTags().size() ; i++){
            createTag(shopNow.getTags(),i);
        }

    }
    //tagCreate
    public void createTag(ArrayList<String> tags, int position){
        String nowText = tags.get(position).toString();

        TextView tv = new TextView(this);
        tv.setText(nowText);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        param.leftMargin = 7;
        tv.setLayoutParams(param);
        tv.setTextSize(15);
        tv.setBackgroundColor(Color.rgb(240,240,240));

        //만든 textview를 추가함
        this.ll_tag.addView(tv);
    }

    //이름 주는 method

    public String getShopTitle( ){
        String name = "pos";
        int pos = intent.getIntExtra(name,0);
        Shops shop = f1.getInfo(pos);
        String num = shop.getT();
        //번호에서 - 삭제
        return num;
    }
    //번호 주는 method
    public String getNum( ){
        String name = "pos";
        int pos = intent.getIntExtra(name,0);
        Shops shop = f1.getInfo(pos);
        String num = shop.getPhone();
        //번호에서 - 삭제
        num.replaceAll("-","");
        return num;
    }
    //선택
    public boolean getChoose(){
        if (chooseOriginal == true){
            return true;
        } else {
            return false;
        }
    }
    public int getPos(){
        return pos;
    }












}