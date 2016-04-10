package com.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.test.chattest.R;
import com.test.controller.MeinfoController;

/**
 * Created by wzy on 2016/3/23.
 */
public class MeinfoView extends LinearLayout {

    private Context mContext;


    private ImageView photo;

    private RelativeLayout userinfo;

    private RelativeLayout setting;

    private RelativeLayout logout;


    public MeinfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initModule(View view) {
        photo = (ImageView) view.findViewById(R.id.photo_info_img);
        userinfo = (RelativeLayout) view.findViewById(R.id.info_meinfo_layout);
        setting = (RelativeLayout) view.findViewById(R.id.setting_meinfo_layout);
        logout = (RelativeLayout) view.findViewById(R.id.logout_meinfo_layout);
    }

    public void setOnClickListener(MeinfoController meinfoController) {
        photo.setOnClickListener(meinfoController);
        userinfo.setOnClickListener(meinfoController);
        setting.setOnClickListener(meinfoController);
        logout.setOnClickListener(meinfoController);
    }
}
