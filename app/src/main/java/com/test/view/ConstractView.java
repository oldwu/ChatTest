package com.test.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.test.adapter.ConstractListAdapter;
import com.test.chattest.ChatActivity;
import com.test.chattest.R;
import com.test.controller.ConstractController;

/**
 * Created by wzy on 16-4-7.
 */
public class ConstractView {

    private View view;
    private Fragment fragment;

    private ListView constractListView;

    public ConstractView(Fragment fragment, View view) {
        this.fragment = fragment;
        this.view = view;
    }

    public void initModule(){
        constractListView = (ListView) view.findViewById(R.id.constract_listview);
    }

    public void setAdapter(ConstractListAdapter adapter){
        constractListView.setAdapter(adapter);
    }

    public void setOnItemClickListener(ConstractController constractController){
        constractListView.setOnItemClickListener(constractController);
    }

    public void setOnItemLongClickListener(ConstractController constractController){
        constractListView.setOnItemLongClickListener(constractController);
    }

    public void startChatActivity(String username){
        Intent intent = new Intent(fragment.getActivity(), ChatActivity.class);
        intent.putExtra("username", username);
        fragment.startActivityForResult(intent, 1);

    }

}
