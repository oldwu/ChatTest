package com.test.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.test.chattest.R;
import com.test.view.MainView;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by wzy on 2016/3/21.
 */
public class MainController implements View.OnClickListener {

    private Context mContext;
    private MainView mMainView;


    public MainController(MainView mainView, Context context) {

        this.mContext = context;
        this.mMainView = mainView;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_main_btn:
                mMainView.setCurrentItem(0);
                break;
            case R.id.contacts_main_btn:
                mMainView.setCurrentItem(1);
                break;
            case R.id.meinfo_main_btn:
                mMainView.setCurrentItem(2);
                break;
            case R.id.addfriend_head_image:
                addFriendDialog();

        }
    }


    public void addFriendDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.dialog_addfriend, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);

        dialogBuilder.setTitle("添加好友");
        dialogBuilder.setView(view);
        dialogBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mContext, "确认", Toast.LENGTH_SHORT).show();

                EditText id = (EditText) view.findViewById(R.id.id_addfriend_edit);
                EditText confirm = (EditText) view.findViewById(R.id.confirminfo_addfriend_edit);

                String idStr = id.getText().toString();
                String confireStr = confirm.getText().toString();
                Conversation conv = JMessageClient.getSingleConversation(idStr);
                if (null == conv) {
                    conv = Conversation.createSingleConversation(idStr);
                }
                TextContent textContent = new TextContent("##好友验证：" + confireStr);
                Message message = conv.createSendMessage(textContent);

                JMessageClient.sendMessage(message);
                JMessageClient.deleteSingleConversation(idStr);
            }
        });
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.show();
    }


}
