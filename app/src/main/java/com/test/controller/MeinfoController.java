package com.test.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.test.chattest.MeInfoFragment;
import com.test.chattest.R;
import com.test.view.MeinfoView;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by wzy on 2016/3/23.
 */
public class MeinfoController implements View.OnClickListener {

    private MeInfoFragment mContext;
    private MeinfoView mMeinfoView;

    public MeinfoController(MeInfoFragment mContext, MeinfoView mMeinfoView) {
        this.mContext = mContext;
        this.mMeinfoView = mMeinfoView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_info_img:
                break;
            case R.id.info_meinfo_layout:

                Toast.makeText(mContext.getActivity(), "info", Toast.LENGTH_SHORT).show();

                break;
            case R.id.setting_meinfo_layout:
                break;
            case R.id.logout_meinfo_layout:

                JMessageClient.logout();
                mContext.startLoginActvity();
                break;
        }
    }
}
