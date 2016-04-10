package com.test.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.beans.ChatContent;
import com.test.chattest.R;
import com.test.tools.MediaManager;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by wzy on 2016/3/31.
 */
public class ChatContentListAdapter extends BaseAdapter {


    private View aniView;

    private List<ChatContent> chatContentList;

    private LayoutInflater inflater;

    public ChatContentListAdapter(Context context, List<ChatContent> chatContentList) {
        this.chatContentList = chatContentList;
        inflater = LayoutInflater.from(context);
    }

    public void addContent(ChatContent chatContent) {
        chatContentList.add(chatContent);
    }

    @Override
    public int getCount() {
        return chatContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatContent chatContent = chatContentList.get(position);

        if (chatContent.getChattype() == ChatContent.TYPE.TO)
            return 0;
        if (chatContent.getChattype() == ChatContent.TYPE.FROM)
            return 1;
        if (chatContent.getChattype() == ChatContent.TYPE.VOICE_TO)
            return 2;
        if (chatContent.getChattype() == ChatContent.TYPE.VOICE_FROM)
            return 3;

        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        ChatContent.TYPE type = chatContentList.get(position).getChattype();


        if (convertView == null) {
            holder = new ViewHolder();


            if (type == ChatContent.TYPE.TO) {
                convertView = inflater.inflate(R.layout.chatto_item, null);
                findViewText(convertView, holder, chatContentList.get(position).getChattype());
            }
            if (type == ChatContent.TYPE.FROM) {
                convertView = inflater.inflate(R.layout.chatfrom_item, null);
                findViewText(convertView, holder, chatContentList.get(position).getChattype());

            }
            if (type == ChatContent.TYPE.VOICE_TO) {
                convertView = inflater.inflate(R.layout.chatto_item, null);
                findViewVoice(convertView, holder, chatContentList.get(position).getChattype());
            }
            if (type == ChatContent.TYPE.VOICE_FROM) {
                convertView = inflater.inflate(R.layout.chatfrom_item, null);
                findViewVoice(convertView, holder, chatContentList.get(position).getChattype());
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        timeVisableControl();

        ChatContent chatContent = chatContentList.get(position);
//        holder.headIcon.setImageBitmap(chatContent.getHeadIcon());
        holder.username.setText(chatContent.getUsername());
        holder.date.setText(chatContent.getDate());

        if (type == ChatContent.TYPE.TO || type == ChatContent.TYPE.FROM)
            holder.chatContent.setText(chatContent.getChatText());
        else {
            holder.voiceTime.setText(Integer.toString(chatContent.getVoiceTime()) + "'");

        }

        if (chatContent.isTimeVisable()) {
            holder.date.setVisibility(View.VISIBLE);
        } else {
            holder.date.setVisibility(View.GONE);
        }

        holder.username.setVisibility(View.GONE);

        //设置语音消息气泡的点击监听
        final View finalConvertView = convertView;
        if (type == ChatContent.TYPE.VOICE_TO) {
            holder.bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaManager.release();

                    if (aniView != null){
                        aniView.setBackgroundResource(R.mipmap.send_3);
                        aniView = null;
                    }

                    aniView = finalConvertView.findViewById(R.id.chatAudio_chatto_img);
                    aniView.setBackgroundResource(R.drawable.voice_send);
                    AnimationDrawable ani = (AnimationDrawable) aniView.getBackground();
                    ani.start();

                    MediaManager.playSound(chatContentList.get(position).getVoiceFilePath(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            aniView.setBackgroundResource(R.mipmap.send_3);
                        }
                    });
                }
            });
        }else if (type == ChatContent.TYPE.VOICE_FROM){
            holder.bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaManager.release();
                    if (aniView != null){
                        aniView.setBackgroundResource(R.mipmap.receive_3);
                        aniView = null;
                    }

                    aniView= finalConvertView.findViewById(R.id.chatAudio_chatfrom_img);
                    aniView.setBackgroundResource(R.drawable.voice_receive);
                    AnimationDrawable ani = (AnimationDrawable) aniView.getBackground();
                    ani.start();

                    MediaManager.playSound(chatContentList.get(position).getVoiceFilePath(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            aniView.setBackgroundResource(R.mipmap.receive_3);
                        }
                    });
                }
            });
        }

        return convertView;
    }


    private void timeVisableControl() {
        for (int i = 1; i < chatContentList.size(); i++) {
            if ((chatContentList.get(i).getDateMs() - chatContentList.get(i - 1).getDateMs()) > 60000) {
                chatContentList.get(i).setTimeVisable(true);
            } else {
                chatContentList.get(i).setTimeVisable(false);
            }
        }
    }

    /**
     * 寻找文本消息相关的view
     * @param convertView
     * @param holder
     * @param type
     */
    private void findViewText(View convertView, ViewHolder holder, ChatContent.TYPE type) {

        if (type == ChatContent.TYPE.TO) {

            holder.chatContent = (TextView) convertView.findViewById(R.id.chatcontent_chatto_tv);
            holder.date = (TextView) convertView.findViewById(R.id.date_chatto_tv);
            holder.username = (TextView) convertView.findViewById(R.id.username_chatto_tv);
            holder.headIcon = (ImageView) convertView.findViewById(R.id.headicon_chatto_img);
            holder.voiceTime = (TextView) convertView.findViewById(R.id.voicesecond_chatto_tv);
            holder.bg = (RelativeLayout) convertView.findViewById(R.id.chatAudio_chatto_bg);
        } else {

            holder.chatContent = (TextView) convertView.findViewById(R.id.chatcontent_chatfrom_tv);
            holder.date = (TextView) convertView.findViewById(R.id.date_chatfrom_tv);
            holder.username = (TextView) convertView.findViewById(R.id.username_chatfrom_tv);
            holder.headIcon = (ImageView) convertView.findViewById(R.id.headicon_chatfrom_img);
            holder.voiceTime = (TextView) convertView.findViewById(R.id.voicesecond_chatfrom_tv);
            holder.bg = (RelativeLayout) convertView.findViewById(R.id.chatAudio_chatfrom_bg);
        }
        holder.chatContent.setVisibility(View.VISIBLE);
        holder.headIcon.setVisibility(View.VISIBLE);
        holder.voiceTime.setVisibility(View.GONE);
        holder.bg.setVisibility(View.GONE);
    }

    /**
     * 寻找语音相关的view
     * @param convertView
     * @param holder
     * @param type
     */
    private void findViewVoice(View convertView, ViewHolder holder, ChatContent.TYPE type) {
        if (type == ChatContent.TYPE.VOICE_TO) {

            holder.chatContent = (TextView) convertView.findViewById(R.id.chatcontent_chatto_tv);
            holder.date = (TextView) convertView.findViewById(R.id.date_chatto_tv);
            holder.username = (TextView) convertView.findViewById(R.id.username_chatto_tv);
            holder.headIcon = (ImageView) convertView.findViewById(R.id.headicon_chatto_img);
            holder.voiceTime = (TextView) convertView.findViewById(R.id.voicesecond_chatto_tv);
            holder.bg = (RelativeLayout) convertView.findViewById(R.id.chatAudio_chatto_bg);
        } else {

            holder.chatContent = (TextView) convertView.findViewById(R.id.chatcontent_chatfrom_tv);
            holder.date = (TextView) convertView.findViewById(R.id.date_chatfrom_tv);
            holder.username = (TextView) convertView.findViewById(R.id.username_chatfrom_tv);
            holder.headIcon = (ImageView) convertView.findViewById(R.id.headicon_chatfrom_img);
            holder.voiceTime = (TextView) convertView.findViewById(R.id.voicesecond_chatfrom_tv);
            holder.bg = (RelativeLayout) convertView.findViewById(R.id.chatAudio_chatfrom_bg);
        }
        //设置不同可见性
        holder.chatContent.setVisibility(View.GONE);
        holder.headIcon.setVisibility(View.VISIBLE);
        holder.voiceTime.setVisibility(View.VISIBLE);
        holder.bg.setVisibility(View.VISIBLE);
    }


    private static class ViewHolder {
        public ImageView headIcon;
        public TextView username;
        public TextView date;
        public TextView chatContent;
        public TextView voiceTime;
        public RelativeLayout bg;
    }


}
