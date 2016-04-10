package com.test.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.test.adapter.ConversationListAdapter;
import com.test.application.ChatTestApplication;
import com.test.beans.UserBean;
import com.test.chattest.ChatActivity;
import com.test.chattest.ConversationListFragment;
import com.test.chattest.R;
import com.test.dao.UserDao;
import com.test.view.ConversationListView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by wzy on 2016/3/23.
 */
public class ConversationListController implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private List<Conversation> mData;
    private ConversationListFragment mContext;
    private ConversationListAdapter mConversationListAdapter;
    private ConversationListView mConvListView;


    public ConversationListController(ConversationListView listView, ConversationListFragment context) {
        mContext = context;
        mConvListView = listView;
        initConvListAdapter();
    }


    private void initConvListAdapter() {
        mData = JMessageClient.getConversationList();
        mConversationListAdapter = new ConversationListAdapter(mContext.getActivity(), mData);
        mConvListView.setConvListAdapter(mConversationListAdapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Conversation conversation = mData.get(position);
        String username = conversation.getTitle();
        MessageContent content = conversation.getLatestMessage().getContent();

        if (TextContent.class.isInstance(content)) {
            String lastContent = ((TextContent)content).getText();
            if (lastContent.length() > 2 && lastContent.substring(0, 2).equals("##")) {
                confireFriendDialog(username, lastContent);
            } else {
                mConvListView.startChatActivity(username);
            }
        }else{
            mConvListView.startChatActivity(username);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deletConversationDialog(mContext.getContext(), position);

        return true;
    }


    public void updateList() {
        mData = JMessageClient.getConversationList();
        mConversationListAdapter.setAllDatas(mData);
        mConversationListAdapter.notifyDataSetChanged();
    }

    public void confireFriendDialog(final String username, String confirmInfo) {
        LayoutInflater inflater = LayoutInflater.from(mContext.getContext());
        View view = inflater.inflate(R.layout.dialog_confirmfriend, null);
        TextView confireTextView = (TextView) view.findViewById(R.id.confireinfo_confirefriend_tv);
        confireTextView.setText(confirmInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getContext());
        builder.setTitle("验证信息");
        builder.setView(view);

        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Conversation conv = JMessageClient.getSingleConversation(username);
                if (null == conv) {
                    conv = Conversation.createSingleConversation(username);
                }
                TextContent textContent = new TextContent("我已同意你的好友请求！");
                Message message = conv.createSendMessage(textContent);
                JMessageClient.sendMessage(message);
                JMessageClient.deleteSingleConversation(username);
                updateList();
                addToFriendDB(username);
            }
        });

        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Conversation conv = JMessageClient.getSingleConversation(username);
                if (null == conv) {
                    conv = Conversation.createSingleConversation(username);
                }
                TextContent textContent = new TextContent("已拒绝");
                Message message = conv.createSendMessage(textContent);
                JMessageClient.sendMessage(message);
                JMessageClient.deleteSingleConversation(username);
                updateList();
            }
        });

        builder.show();
    }

    /**
     * 添加到好友表中
     */
    public void addToFriendDB(String username){
        UserDao userDao = new UserDao(mContext.getActivity());
        UserBean userBean = new UserBean();
        userBean.setUsername(username);
        userDao.add(userBean);
        ConstractController.updateView(userDao.list());
    }

    public void deletConversationDialog(final Context context, final int postion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("确认删除会话？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                JMessageClient.deleteSingleConversation(mData.get(postion).getTitle());
                mConversationListAdapter.deleteData(postion);
                mConversationListAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }



}
