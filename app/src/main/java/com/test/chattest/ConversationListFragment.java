package com.test.chattest;


import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.application.ChatTestApplication;
import com.test.controller.ConversationListController;
import com.test.tools.ContentManager;
import com.test.view.ConversationListView;


import org.w3c.dom.Text;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by wzy on 2016/3/21.
 */
public class ConversationListFragment extends Fragment {

    private ConversationListView mConversationListView;
    private ConversationListController mConversationListController;


    private Message jMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        mConversationListView = new ConversationListView(view, this);
        mConversationListView.initModule();
        mConversationListController = new ConversationListController(mConversationListView, this);
        mConversationListView.setOnItemClickListener(mConversationListController);
        mConversationListView.setOnItemLongClickListener(mConversationListController);


        JMessageClient.registerEventReceiver(this);
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        mConversationListController.updateList();
        System.out.println("Hi");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            mConversationListView.initModule();
            mConversationListController = new ConversationListController(mConversationListView, this);
            mConversationListView.setOnItemClickListener(mConversationListController);
            mConversationListView.setOnItemLongClickListener(mConversationListController);
        }
    }

    public void onEvent(MessageEvent event) {
        jMessage = event.getMessage();
        MessageContent content = jMessage.getContent();

        if (ContentManager.getInstance().isTextContent(content)) {
            TextContent textContent = (TextContent) content;
            if (textContent.getText().substring(0, 2).equals("##")) {
                JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_NO_NOTIFICATION);
                android.os.Message message = new android.os.Message();
                message.what = ChatTestApplication.GET_MESSAGE_ADDFRIEND;
                message.obj = textContent.getText();
                mHandler.sendMessage(message);
            } else {
                mHandler.sendEmptyMessage(ChatTestApplication.GET_MESSAGE_CHAT);
            }

            if (textContent.getText().equals("我已同意你的好友请求！")) {
                mConversationListController.addToFriendDB(jMessage.getFromUser().getUserName());
            }
        }


    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == ChatTestApplication.GET_MESSAGE_CHAT) {
                mConversationListController.updateList();
            } else if (msg.what == ChatTestApplication.GET_MESSAGE_ADDFRIEND) {
                String username = jMessage.getFromUser().getUserName();
                String confirmInfo = msg.obj.toString();
                mConversationListController.confireFriendDialog(username, confirmInfo);
            } else if (msg.what == ChatTestApplication.GET_MESSAGE_UPDATE_CONVERSATIONLIST) {
                mConversationListController.updateList();
            }

        }
    };


}
