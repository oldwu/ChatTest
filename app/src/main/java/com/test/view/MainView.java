package com.test.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.test.adapter.MainViewPagerAdapter;
import com.test.chattest.R;
import com.test.controller.MainController;

/**
 * Created by wzy on 2016/3/21.
 */
public class MainView extends RelativeLayout {
    private Context mContext;

    private ImageButton[] mBtnList;
    private int[] mBtnListID;
    private ImageButton addFriends;


    private ViewPager viewPager;


    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void initModule() {

        addFriends = (ImageButton) findViewById(R.id.addfriend_head_image);
        mBtnListID = new int[]{R.id.chat_main_btn, R.id.contacts_main_btn, R.id.meinfo_main_btn};
        mBtnList = new ImageButton[3];
        for (int i = 0; i < 3; i++) {
            mBtnList[i] = (ImageButton) findViewById(mBtnListID[i]);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mBtnList[0].setBackgroundResource(R.mipmap.actionbar_msg_sel);
                        mBtnList[1].setBackgroundResource(R.mipmap.actionbar_contact);
                        mBtnList[2].setBackgroundResource(R.mipmap.actionbar_me);
                        break;
                    case 1:
                        mBtnList[0].setBackgroundResource(R.mipmap.actionbar_msg);
                        mBtnList[1].setBackgroundResource(R.mipmap.actionbar_contact_sel);
                        mBtnList[2].setBackgroundResource(R.mipmap.actionbar_me);
                        break;
                    case 2:
                        mBtnList[0].setBackgroundResource(R.mipmap.actionbar_msg);
                        mBtnList[1].setBackgroundResource(R.mipmap.actionbar_contact);
                        mBtnList[2].setBackgroundResource(R.mipmap.actionbar_me_sel);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void setCurrentItem(int id) {
        viewPager.setCurrentItem(id);
    }


    public void setOnClickListener(MainController mController) {
        for (int i = 0; i < 3; i++) {
            mBtnList[i].setOnClickListener(mController);
        }
        addFriends.setOnClickListener(mController);
    }

    public void setViewPagerAdapter(MainViewPagerAdapter mainViewPagerAdapter) {
        viewPager.setAdapter(mainViewPagerAdapter);
    }



}
