package com.test.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.test.chattest.R;
import com.test.view.RegisterView;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by wzy on 2016/3/21.
 */
public class RegisterController implements View.OnClickListener {

    private RegisterView mRegisterView;
    private Context mContext;

    public RegisterController(RegisterView registerView, Context context) {
        this.mRegisterView = registerView;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        String account = mRegisterView.getAccount();
        String password = mRegisterView.getPassword();

        if (v.getId() == R.id.reg_register_btn) {
            JMessageClient.register(account, password, new BasicCallback() {
                @Override
                public void gotResult(int status, String s) {
                    if (status == 0) {
                        Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                        mRegisterView.back();
                    }else {
                        Toast.makeText(mContext, "用户名不合法", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
