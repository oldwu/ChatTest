package com.test.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.test.chattest.LoginActivity;
import com.test.chattest.R;
import com.test.chattest.RegisterActivity;
import com.test.tools.DialogCreater;
import com.test.view.LoginView;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by wzy on 2016/3/21.
 */
public class LoginController implements View.OnClickListener {

    private LoginView mLoginView;
    private LoginActivity mContext;

    public LoginController(LoginView loginView, Context context) {

        this.mLoginView = loginView;
        this.mContext = (LoginActivity)context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_btn:

                String username = mLoginView.getUsername();
                String password = mLoginView.getPassword();
                InputMethodManager manager = ((InputMethodManager) mContext
                        .getSystemService(Activity.INPUT_METHOD_SERVICE));
                if (mContext.getWindow().getAttributes().softInputMode
                        != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (mContext.getCurrentFocus() != null) {
                        manager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }

                final Dialog dialog = DialogCreater.createLoadingDialog(mContext,
                        "正在登录");
                dialog.show();
                JMessageClient.login(username, password, new BasicCallback() {
                    @Override
                    public void gotResult(int status, String s) {
                        if (status == 0) {
                            Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            mContext.startMainActivity();
                        } else {
                            Toast.makeText(mContext, "用户名不存在或者密码错误", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });

                break;
            case R.id.reg_login_btn:
                mContext.startRegisterActivity();
                break;
        }
    }

}
