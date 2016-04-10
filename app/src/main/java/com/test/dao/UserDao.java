package com.test.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.test.application.ChatTestApplication;
import com.test.beans.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzy on 16-4-6.
 */
public class UserDao {

    private FriendDBHelper dbHelper;
    private String username;

    public UserDao(Context context) {
        username = ChatTestApplication.USERNAME;
        dbHelper = new FriendDBHelper(context);
        dbHelper.onCreate(dbHelper.getWritableDatabase());
    }

    public void add(UserBean userBean) {
        String sql = "insert into " + username + "_friend(username) values(?)";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[]{userBean.getUsername()});
        db.close();
    }

    public void delete(String username) {
        String sql = "delete from " + username + "_friend where username = '?'";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[]{username});
        db.close();
    }

    public List<UserBean> list() {
        List<UserBean> userBeans = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(username + "_friend", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                c.move(i);
                String username = c.getString(1);
                UserBean userBean = new UserBean();
                userBean.setUsername(username);
                userBeans.add(userBean);
            }
        }
        return userBeans;
    }

}
