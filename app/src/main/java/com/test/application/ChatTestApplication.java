package com.test.application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by wzy on 2016/3/21.
 */
public class ChatTestApplication extends Application {


    public static final int PAGE_MESSAGE_COUNT = 18;

    public static final int GET_MESSAGE_CHAT = 0;

    public static final int GET_MESSAGE_ADDFRIEND = 1;

    public static final int GET_MESSAGE_ADDFRIENDRETURN = 2;

    public static final int GET_MESSAGE_UPDATE_CONVERSATIONLIST = 3;

    public static String USERNAME = null;



    @Override
    public void onCreate() {
        super.onCreate();

        JMessageClient.init(getApplicationContext());
        JPushInterface.setDebugMode(true);


        //设置Notification的模式
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
        //注册Notification点击的接收器
    }
}
