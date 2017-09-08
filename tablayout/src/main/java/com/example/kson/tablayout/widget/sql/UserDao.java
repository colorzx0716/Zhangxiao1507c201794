package com.example.kson.tablayout.widget.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 张肖肖 on 2017/9/6.
 */

public class UserDao {

    private final MySqliteOpenHelper helper;
    private final Context context;
    private String title;

    public UserDao(Context context) {
       this.context = context;
        helper = new MySqliteOpenHelper(context);
    }

    //增加
    public void add(String title){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        db.insert("user",null,values);
        db.close();
    }

    //查询
    public String query(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            title = cursor.getString(cursor.getColumnIndex("title"));
            System.out.println("title = " + title);
        }
        return title;//返回结果
    }




}
