package com.test.chattest;

import android.app.Activity;
import android.os.Bundle;

import com.test.controller.RegisterController;
import com.test.view.RegisterView;

/**
 * Created by wzy on 2016/3/21.
 */
public class RegisterActivity extends Activity {

    private RegisterView mRegisterView;
    private RegisterController mRegisterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        mRegisterView = (RegisterView)findViewById(R.id.registerview);
        mRegisterView.initModule();
        mRegisterController = new RegisterController(mRegisterView, RegisterActivity.this);
        mRegisterView.setOnClickListener(mRegisterController);

    }

}
