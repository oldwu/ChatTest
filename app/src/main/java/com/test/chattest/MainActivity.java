package com.test.chattest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.test.adapter.MainViewPagerAdapter;
import com.test.application.ChatTestApplication;
import com.test.controller.MainController;
import com.test.view.MainView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

public class MainActivity extends FragmentActivity {

    private MainView mMainView;
    private MainController mMainController;

    private Fragment chatFragment;
    private Fragment constractFragment;
    private Fragment meInfoFragment;

    private List<Fragment> fragmentList;
    private MainViewPagerAdapter mMainViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainView = (MainView) findViewById(R.id.mainview);
        mMainView.initModule();
        mMainController = new MainController(mMainView, MainActivity.this);
        mMainView.setOnClickListener(mMainController);


        fragmentList = new ArrayList<>();
        chatFragment = new ConversationListFragment();
        constractFragment = new ConstractFragment();
        meInfoFragment = new MeInfoFragment();
        fragmentList.add(chatFragment);
        fragmentList.add(constractFragment);
        fragmentList.add(meInfoFragment);
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mMainView.setViewPagerAdapter(mMainViewPagerAdapter);

        ((ConstractFragment)constractFragment).getConstractController();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(MainActivity.this);

        UserInfo userInfo  = JMessageClient.getMyInfo();

        if (userInfo == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            ChatTestApplication.USERNAME = userInfo.getUserName();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
        System.out.println("Destroy");
    }



}
