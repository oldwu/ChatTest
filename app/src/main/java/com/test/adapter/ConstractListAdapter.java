package com.test.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.beans.UserBean;
import com.test.chattest.R;
import com.test.dao.UserDao;

import java.util.List;

/**
 * Created by wzy on 16-4-7.
 */
public class ConstractListAdapter extends BaseAdapter {

    private List<UserBean> userBeans;
    private Context mContext;
    private LayoutInflater inflater;


    public ConstractListAdapter(Context mContext, List<UserBean> userBeans) {

        this.mContext = mContext;
        this.userBeans = userBeans;

    }

    public void setData(List<UserBean> mData) {
        this.userBeans = mData;
    }

    public void deleteData(int position){

        UserDao userDao = new UserDao(mContext);
        userDao.delete(userBeans.get(position).getUsername());
        userBeans.remove(position);

    }


    @Override
    public int getCount() {
        return userBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return userBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = LayoutInflater.from(mContext);
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.constract_item, null);
            holder = new ViewHolder();
            holder.headIcon = (ImageView) convertView.findViewById(R.id.headicon_constract_img);
            holder.username = (TextView) convertView.findViewById(R.id.username_constract_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserBean userBean = userBeans.get(position);
        holder.username.setText(userBean.getUsername());

        return convertView;
    }


    private class ViewHolder {

        public ImageView headIcon;
        public TextView username;

    }
}
