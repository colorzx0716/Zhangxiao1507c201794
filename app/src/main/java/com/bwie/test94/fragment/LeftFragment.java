package com.bwie.test94.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.bwie.test94.R;
import com.bwie.test94.SuccessActivity;
import com.example.city_picker.CityListActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 张肖肖 on 2017/9/4.
 */

public class LeftFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private Button lixian,btn_city;
    private Switch sw;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = View.inflate(getActivity(), R.layout.left_item,null);
        lixian = mRootView.findViewById(R.id.btn_lixian);
         btn_city = mRootView.findViewById(R.id.btn_city);
        sw = mRootView.findViewById(R.id.swith_onof);

        lixian.setOnClickListener(this);
        btn_city.setOnClickListener(this);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){//开
                    JPushInterface.resumePush(getContext());
                }else{//关
                    JPushInterface.stopPush(getContext());
                }
            }
        });

        return mRootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lixian:
                //点击按钮跳转到下载页面
                Intent intent = new Intent(getActivity(), SuccessActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_city:
                CityListActivity.startCityActivityForResult(getActivity());
                break;


        }
    }
}
