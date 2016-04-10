package com.test.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.test.adapter.ConversationListAdapter;
import com.test.chattest.ChatActivity;
import com.test.chattest.ConversationListFragment;
import com.test.chattest.R;
import com.test.controller.ConversationListController;

/**
 * Created by wzy on 2016/3/23.
 */
public class ConversationListView {

    private View mConversationListFragment;
    private Fragment fragment;

    private ListView mListView;


    public ConversationListView(View view, Fragment fragment) {
        mConversationListFragment = view;
        this.fragment = fragment;
    }

    public void initModule() {
        mListView = (ListView) mConversationListFragment.findViewById(R.id.chatlist_chat_lsv);
    }


    public void setOnItemClickListener(ConversationListController conversationListController) {
        mListView.setOnItemClickListener(conversationListController);
    }

    public void setOnItemLongClickListener(ConversationListController conversationListController) {
        mListView.setOnItemLongClickListener(conversationListController);
    }

    public void setConvListAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    public void startChatActivity(String username){
        Intent intent = new Intent(fragment.getActivity(), ChatActivity.class);
        intent.putExtra("username", username);
        fragment.startActivityForResult(intent, 1);

    }
}
