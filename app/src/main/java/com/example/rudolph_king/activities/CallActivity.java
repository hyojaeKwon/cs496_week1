package com.example.rudolph_king.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rudolph_king.Shops;
import com.example.rudolph_king.fragments.Fragment1;

import com.example.rudolph_king.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class CallActivity extends AppCompatActivity {

    public TextView tv_isOpen;
    public TextView tv_name;
    public TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        setItems();
    }

    //지도 띄우는 코드
    private void initView(Shops shop){
        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        //지도 추가하는 코드
        mapViewContainer.addView(mapView);
        //가게 위치를 point로 저장
        MapPoint mp = MapPoint.mapPointWithGeoCoord(shop.getLatitude(), shop.getLongitude());
        //가게의 위치를 지도에 추가하는 코드
        mapView.setMapCenterPoint(mp, true);
        mapView.setZoomLevel(4,true);

        //줌인 줌아웃 허용
        mapView.zoomOut(true);
        mapView.zoomIn(true);

        //마커 생성 코드
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(shop.getT());
        marker.setTag(0);
        marker.setMapPoint(mp);
        //마커 모양 설정
        marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);

    }


    Intent intent = new Intent();
    String name = "pos";
    int pos = intent.getIntExtra(name,0);
    Fragment1 f1  = new Fragment1();
    private boolean chooseOriginal = f1.isFilteredListEmpty();

    public void setItems(){

        Shops shopNow = f1.getInfo(pos);
        this.tv_isOpen = (TextView) findViewById(R.id.call_isOpen);
        this.tv_name = (TextView) findViewById(R.id.title_of_shop_detail);
        this.tv_phone = (TextView) findViewById(R.id.call_phone);

        if (shopNow.getIsOpen() == true){
            tv_isOpen.setText("영업 중");
        } else {
            tv_isOpen.setText("영업 종료");
        }
        tv_name.setText(shopNow.getT());
        tv_phone.setText(shopNow.getPhone());

        initView(shopNow);

    }
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