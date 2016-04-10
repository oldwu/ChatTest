package com.test.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.chattest.R;
import com.test.tools.ContentManager;
import com.test.tools.TimeFormat;

import java.util.List;

import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by wzy on 2016/3/23.
 */
public class ConversationListAdapter extends BaseAdapter {


    private List<Conversation> conversationList;
    private LayoutInflater mInflater;
    private Activity mContext;


    public ConversationListAdapter(Activity context, List<Conversation> conversationList) {
        this.conversationList = conversationList;
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void setAllDatas(List<Conversation> data) {
        conversationList = data;
    }

    public void deleteData(int position){
        conversationList.remove(position);
    }


    @Override
    public int getCount() {

        if (conversationList == null) {
            return 0;
        }
        return conversationList.size();
    }

    @Override
    public Object getItem(int position) {

        if (conversationList == null) {
            return null;
        }
        return conversationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.chatlist_item, null);
            holder.headImg = (ImageView) convertView.findViewById(R.id.headimg_chatlist_img);
            holder.username = (TextView) convertView.findViewById(R.id.username_chatlist_tv);
            holder.date = (TextView) convertView.findViewById(R.id.date_chatlist_tv);
            holder.content = (TextView) convertView.findViewById(R.id.content_chatlist_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Conversation conversation = conversationList.get(position);
//        holder.headImg.setImageBitmap(BitmapFactory.decodeFile(conversation.getAvatarFile().getPath()));
        holder.username.setText(conversation.getTitle());

        TimeFormat timeFormat = new TimeFormat(mContext, conversation.getLastMsgDate());
        holder.date.setText(timeFormat.getTime());

        if (ContentManager.getInstance().isTextContent(conversation.getLatestMessage().getContent()))
            holder.content.setText(((TextContent) conversation.getLatestMessage().getContent()).getText());
        else{
            holder.content.setText("[语音]");
        }


        UserInfo userInfo = (UserInfo) conversation.getTargetInfo();
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())) {
            userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int status, String desc, Bitmap bitmap) {
                    if (status == 0) {
                        holder.headImg.setImageBitmap(bitmap);
                    }
                }
            });
        }

        return convertView;
    }

    final private class ViewHolder {
        public ImageView headImg;
        public TextView username;
        public TextView date;
        public TextView content;

    }
}
