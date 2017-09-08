package com.bwie.test94;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bwie.test94.adapter.MyAdapter;
import com.bwie.test94.bean.Catogray;
import com.bwie.test94.sql.DbHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SuccessActivity extends AppCompatActivity {
    private RecyclerView lv;
    private Button btn_down;
    //必须导入依赖才能用
    private List<Catogray> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initView();
    }

    //初始化数据
    private void initData() {
        list = new ArrayList<>();
        Catogray c = new Catogray();
        c.type = "top";
        c.name = "第一季";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "第二季";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "第三季";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "第四季";
        list.add(c);
        c = new Catogray();
        c.type = "top";
        c.name = "第五季";
        list.add(c);

        MyAdapter adapter = new MyAdapter(this, list);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos, View view) {
                CheckBox checkbox = view.findViewById(R.id.checkbox);
                Catogray c = list.get(pos);
                if (checkbox.isChecked()) {
                    Toast.makeText(SuccessActivity.this, "我被取消了", Toast.LENGTH_SHORT).show();
                    checkbox.setChecked(false);
                    c.state = false;
                } else {
                    Toast.makeText(SuccessActivity.this, "我被点击了", Toast.LENGTH_SHORT).show();
                    checkbox.setChecked(true);
                    c.state = true;
                }
                //修改原有list的数据，根据pos，设置新的对象，然后更新list
                list.set(pos, c);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("false = " + list.get(i).state);
                }
            }
        });
    }

    //初始化控件
    private void initView() {
        lv = (RecyclerView) findViewById(R.id.lv);
        initData();// //初始化数据
        btn_down = (Button) findViewById(R.id.btn_down);
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list != null && list.size() > 0){
                    for (Catogray catogray:list) {
                        if (catogray.state) {//判断是否是选中状态，如果选中则执行下载操作
                            Toast.makeText(SuccessActivity.this, "断是否是选中状态，如果选中则执行下载操作", Toast.LENGTH_SHORT).show();
                            loadData(catogray.type);//只要有WiFi，就下载离线数据，下载完成后保存到数据库
                        }
                    }
                }
                for (Catogray catogray : list) {
                    System.out.println("state====" + catogray.state);
                }
            }
        });
    }

    /**
     * 只要有WiFi，就下载离线数据，下载完成后保存到数据库
     * @param type
     */
    private void loadData(final String type) {
        RequestParams params = new RequestParams(Api.NEWS);
        params.addParameter("key", Api.APPKEY);
        params.addParameter("type", type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //下载成功后保存到数据库
                saveData(type,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //保存到数据库的方法
    private void saveData(String type, String result) {
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type",type);
        values.put("content",result);
        db.insert("news",null,values);
    }

}
