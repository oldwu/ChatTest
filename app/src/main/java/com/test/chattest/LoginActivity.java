package com.test.chattest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.test.controller.LoginController;
import com.test.view.LoginView;

/**
 * Created by wzy on 2016/3/21.
 */
public class LoginActivity extends Activity {

    private LoginView mLoginView;
    private LoginController mLoginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginView = (LoginView) findViewById(R.id.loginview);
        mLoginView.initModule();
        mLoginController = new LoginController(mLoginView, this);
        mLoginView.setOnClickListener(mLoginController);
    }

    public void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void startMainActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);

    }


}
