package com.test.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.test.adapter.ConstractListAdapter;
import com.test.beans.UserBean;
import com.test.dao.UserDao;
import com.test.view.ConstractView;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by wzy on 16-4-7.
 */
public class ConstractController implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private Context mContext;
    private ConstractView constractView;
    private static ConstractListAdapter adapter;
    private static List<UserBean> userBeans;


    public ConstractController(ConstractView constractView, Context mContext) {
        this.mContext = mContext;
        this.constractView = constractView;
        initList();
    }

    private void initList() {
        UserDao userDao = new UserDao(mContext);
        userBeans = userDao.list();
        adapter = new ConstractListAdapter(mContext, userBeans);
        constractView.setAdapter(adapter);
    }

    /**
     * 更新好友列表
     */
    public static void updateView(List<UserBean> _userBeans) {
        adapter.setData(_userBeans);
        userBeans = _userBeans;
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String username = userBeans.get(position).getUsername();
        constractView.startChatActivity(username);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deleteFriendDialog(mContext, position);
        return true;
    }

    public void deleteFriendDialog(final Context context, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("是否删除好友？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.deleteData(position);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }


}
