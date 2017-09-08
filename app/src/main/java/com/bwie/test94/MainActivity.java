package com.bwie.test94;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bwie.test94.fragment.Fragment1;
import com.bwie.test94.fragment.LeftFragment;
import com.bwie.test94.fragment.RightFragment;
import com.example.kson.tablayout.widget.HorizontalScollTabhost;
import com.example.kson.tablayout.widget.bean.CategoryBean;
import com.kson.slidingmenu.SlidingMenu;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面。头部横条
 * 左右侧拉
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HorizontalScollTabhost tab;
    private List<CategoryBean> beanList;
    private List<Fragment> fragmentList;
    private ImageView user;
    private ImageView shezhi;
    private SlidingMenu menu;
    private ImageView dot1;
    private List<ChannelBean> channelBeanList;

    private String[] titles = {"推荐","热点","北京","视频","社会",
            "订阅","娱乐","图片","科技","汽车",
            "体育","财经","军事","国际","段子",
            "趣图","美女","健康","正能量","特卖",
            "中国好声音","历史","时尚","辟谣","探索","美国",
            "搞笑","故事","奇葩","情感"};

    private String jsonstr;
    private SharedPreferences pre;
    private CategoryBean bean;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //静态广播发送
        sendBroadcast(new Intent("kson.test"));

        tab = (HorizontalScollTabhost) findViewById(R.id.tab_lin);
        user = (ImageView) findViewById(R.id.user);
        shezhi = (ImageView) findViewById(R.id.shezhi);
        dot1 = (ImageView) findViewById(R.id.dot1);
        //点击图片侧拉
        user.setOnClickListener(this);
        shezhi.setOnClickListener(this);
        dot1.setOnClickListener(this);

        pre = getSharedPreferences("setting",MODE_PRIVATE);//创建sp

        initData();//头部横条
        initMenu();//左右侧拉

    }

    //左右侧拉
    private void initMenu() {
      //setBehindContentView(R.layout.left_menu);
         menu = new SlidingMenu(this);
        menu.setMenu(R.layout.left_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu,new LeftFragment()).commit();

        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);


        menu.setSecondaryMenu(R.layout.right_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu,new RightFragment()).commit();

        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }

    private void initData() {

        //创建两个集合存放数据
        beanList = new ArrayList<>();
        fragmentList = new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            bean = new CategoryBean();
            bean.name = titles[i];
            beanList.add(bean);
            fragmentList.add(new Fragment1());
        }

        tab.diaplay(beanList,fragmentList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user:
                menu.showMenu();
                break;
            case R.id.shezhi:
                menu.showSecondaryMenu();
                break;
            case R.id.dot1://点击频道
                //获取sp中存值
                jsonstr = pre.getString("user_setting",null);
                channelBeanList = new ArrayList<>();
                if(jsonstr == null){
                ChannelBean channelBean;
                for (int i = 0; i <titles.length ; i++) {
                    if(i<10){
                        channelBean = new ChannelBean(titles[i],true);
                        bean.name = titles[i];

                    }else{
                        channelBean = new ChannelBean(titles[i],false);
                    }
                    channelBeanList.add(channelBean);
                }
                }else{
                    //不为空使用之前回传的数据
                    try {
                        JSONArray arr =new JSONArray(jsonstr);
                        System.out.println("arr.toString() = " + arr.toString());
                        for (int i = 0; i <arr.length() ; i++) {
                            JSONObject oo = (JSONObject) arr.get(i);
                            String name = oo.getString("name");
                            boolean isSelected = oo.getBoolean("isSelect");
                            ChannelBean channelBean = new ChannelBean(name,isSelected);
                            channelBeanList.add(channelBean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ChannelActivity.startChannelActivity(MainActivity.this,channelBeanList);
                break;
        }
    }

    //回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);


        //判断回传吗是否相同
        if(requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE){
            //获取回传的值
            jsonstr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
            //存入sp
            pre.edit().putString("user_setting",jsonstr).commit();

            beanList.clear();//清空
            List<Fragment> fragmentList2 = new ArrayList<>();

            try {
                JSONArray arr =new JSONArray(jsonstr);
                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject oo = (JSONObject) arr.get(i);
                    String name = oo.getString("name");
                    boolean isSelected = oo.getBoolean("isSelect");
                   if(isSelected){
                       CategoryBean c = new CategoryBean();
                       c.name = name;

                       beanList.add(c);
                       fragmentList2.add(fragmentList.get(i));
                   }
                }

                tab.remove();
                tab.diaplay(beanList,fragmentList2);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
