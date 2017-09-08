package com.bwie.test94;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 张肖肖 on 2017/9/4.
 */

public class MyApp extends Application {
    { PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");}

    @Override
    public void onCreate() {
        super.onCreate();
        //设置极光推送bug模式
        JPushInterface.setDebugMode(true);
        //初始化极光推送
        JPushInterface.init(this);

        //日夜切换模式
        if (getSharedPreferences("theme", MODE_PRIVATE).getBoolean("night_theme", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        UMShareAPI.get(this);

        initIMG();//图片缓存

        x.Ext.init(this);
        x.Ext.setDebug(false);

    }

    private void initIMG() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration cofig = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(cofig);

    }
}
