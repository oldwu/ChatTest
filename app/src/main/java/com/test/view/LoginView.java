package com.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.test.chattest.R;
import com.test.controller.LoginController;

/**
 * Created by wzy on 2016/3/21.
 */
public class LoginView extends LinearLayout {

    public Context mContext;

    public EditText username;
    public EditText password;

    public Button login;
    public Button register;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initModule() {
        username = (EditText) findViewById(R.id.username_login_eit);
        password = (EditText) findViewById(R.id.password_login_eit);

        login = (Button) findViewById(R.id.login_login_btn);
        register = (Button) findViewById(R.id.reg_login_btn);
    }


    public String getUsername() {
        return username.getText().toString().trim();
    }

    public String getPassword() {
        return password.getText().toString().trim();
    }

    public void setOnClickListener(LoginController loginController){
        login.setOnClickListener(loginController);
        register.setOnClickListener(loginController);

    }



}
