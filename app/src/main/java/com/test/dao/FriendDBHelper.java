package com.test.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.application.ChatTestApplication;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by wzy on 16-4-6.
 */
public class FriendDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "chat_test.db3";

    public FriendDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String username = ChatTestApplication.USERNAME;
        String sql = "create table if not exists " + username + "_friend (id integer primary key autoincrement, username varchar(20) unique)";
        db.execSQL(sql);
        System.out.println("table create !");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
