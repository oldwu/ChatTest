package com.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.test.chattest.R;
import com.test.chattest.RegisterActivity;
import com.test.controller.RegisterController;

/**
 * Created by wzy on 2016/3/21.
 */
public class RegisterView extends LinearLayout {


    private RegisterActivity mContext;

    private EditText mAccount;
    private EditText mPassword;
    private Button mRegister;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (RegisterActivity) context;
    }

    public void initModule() {
        mAccount = (EditText) findViewById(R.id.username_register_eit);
        mPassword = (EditText) findViewById(R.id.password_register_eit);
        mRegister = (Button) findViewById(R.id.reg_register_btn);
    }

    public String getAccount() {
        return mAccount.getText().toString().trim();
    }

    public String getPassword() {
        return mPassword.getText().toString().trim();
    }

    public void setOnClickListener(RegisterController registerController){
        mRegister.setOnClickListener(registerController);
    }

    public void back(){
        mContext.finish();
    }
}
