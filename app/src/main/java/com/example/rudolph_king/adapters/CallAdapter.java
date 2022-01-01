//package com.example.rudolph_king.adapters;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.rudolph_king.R;
//import com.example.rudolph_king.Shops;
//import com.example.rudolph_king.activities.CallActivity;
//import com.example.rudolph_king.fragments.Fragment1;
//
//public class CallAdapter extends LinearLayout implements OnPersonItemClickListener{
//    public int posi;
//    Shops shop;
//    public static CallActivity ca = new CallActivity();
//    Fragment1 f1  = new Fragment1();
//    private Context context;
//    Context callContext;
//    OnPersonItemClickListener listener;
//    LayoutInflater mLayoutInflater = null;
//
//
//    public CallAdapter(Context context){
//        super(context);
//        this.callContext = context;
//        mLayoutInflater = LayoutInflater.from(callContext);
//    }
//    @Override
//    public void onItemClick(CustomAdapter.ViewHolder holder, View view, int position) {
//
//    }
//
//
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.,false)
//    }
//    public class ViewHolder{
//
//
//        public TextView tv_isOpen;
//        public TextView tv_name;
//        public TextView tv_phone;
//
//        public ViewHolder(View view){
//
//            tv_isOpen = view.findViewById(R.id.call_isOpen);
//
//            posi = ca.getPos();
//            Log.e("get_pos",Integer.toString(posi));
////            super(view);
//            if(ca.getChoose()){
//                shop = f1.getShopList().get(posi);
//            } else {
//                shop = f1.getFilteredList().get(posi);
//            }
//
//            if(shop.getIsOpen()){
//                tv_isOpen.setText("영업 중");
//            } else {
//                tv_isOpen.setText("영업 종료");
//            }
//
//            tv_name.setText(shop.getT().toString());
//            tv_phone.setText(shop.getPhone());
//        }
//
//    }
//}
