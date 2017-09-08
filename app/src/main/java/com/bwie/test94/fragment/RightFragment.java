package com.bwie.test94.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.test94.MainActivity;
import com.bwie.test94.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * 在右边侧拉
 * Created by 张肖肖 on 2017/9/4.
 */

public class RightFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       mRootView = View.inflate(getActivity(), R.layout.right_item, null);

        Button QQ = mRootView.findViewById(R.id.btn_qq);
        Button btn_night = mRootView.findViewById(R.id.btn_night);

        //夜间模式
        btn_night.setOnClickListener(this);

        final ImageView qq = mRootView.findViewById(R.id.qq);
        //腾讯QQ按钮点击授权
        QQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //图二点击QQ 第三方登录，获取图像，显示在图一的左上角 区域
                UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
            }
        });

        return mRootView;
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getActivity(), "Authorize succeed", Toast.LENGTH_SHORT).show();

            String img = data.get("iconurl");
            ImageLoader.getInstance().displayImage(img, (ImageView) getActivity().findViewById(R.id.user));

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getActivity(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getActivity(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    //夜间模式
    @Override
    public void onClick(View view) {
        //切换日夜间模式
        int uiMode;
        uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if(uiMode == Configuration.UI_MODE_NIGHT_YES){
            ( (MainActivity)getActivity()).getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            getActivity().getSharedPreferences("theme",MODE_PRIVATE).edit().putBoolean("night_theme",false).commit();

        }else if(uiMode == Configuration.UI_MODE_NIGHT_NO){

            ( (MainActivity)getActivity()).getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            getActivity().getSharedPreferences("theme",MODE_PRIVATE).edit().putBoolean("night_theme", true).commit();

        }

        getActivity().recreate();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
