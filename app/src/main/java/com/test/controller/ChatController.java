package com.test.controller;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.test.adapter.ChatContentListAdapter;
import com.test.application.ChatTestApplication;
import com.test.beans.ChatContent;
import com.test.chattest.ChatActivity;
import com.test.chattest.R;
import com.test.tools.ContentManager;
import com.test.tools.TimeFormat;
import com.test.view.AudioRecordButton;
import com.test.view.ChatView;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by wzy on 2016/3/31.
 */
public class ChatController implements View.OnClickListener, AudioRecordButton.AudioFinishRecorderListener, AdapterView.OnItemClickListener {

    private Context context;
    private ChatView chatView;
    private String title;

    private ChatContentListAdapter chatContentListAdapter;
    private List<ChatContent> chatContentList;

    public static boolean isVoice = false;

    public ChatController(Context context, ChatView chatView, String title) {
        this.context = context;
        this.chatView = chatView;
        this.title = title;
        initChatContentListAdapter(title);
    }


    private void initChatContentListAdapter(String title) {
        Conversation conversation = JMessageClient.getSingleConversation(title);
        chatContentList = new ArrayList<>();
        if (conversation != null) {
            List<Message> messageList = conversation.getAllMessage();
            for (int i = 0; i < messageList.size(); i++) {
                Message msg = messageList.get(i);
                UserInfo userInfo = msg.getFromUser();
                String username = userInfo.getUserName();
                MessageContent content = msg.getContent();

                ChatContent chatContent = new ChatContent();
                if (ContentManager.getInstance().isTextContent(content)) {
                    String chatcontent = ((TextContent) content).getText();
                    ChatContent.TYPE type = (msg.getDirect() == MessageDirect.send ? ChatContent.TYPE.TO : ChatContent.TYPE.FROM);
                    chatContent.setChatText(chatcontent);
                    chatContent.setChattype(type);
                } else {
                    String voiceFilePath = ((VoiceContent) content).getLocalPath();
                    int voiceSecond = ((VoiceContent) content).getDuration();
                    ChatContent.TYPE type = (msg.getDirect() == MessageDirect.send ? ChatContent.TYPE.VOICE_TO : ChatContent.TYPE.VOICE_FROM);
                    chatContent.setVoiceTime(voiceSecond);
                    chatContent.setVoiceFilePath(voiceFilePath);
                    chatContent.setChattype(type);
                }

                long dateMs = msg.getCreateTime();
                TimeFormat timeFormat = new TimeFormat(context, dateMs);
                String date = timeFormat.getTime();
                chatContent.setDateMs(dateMs);
                chatContent.setDate(date);
                chatContent.setUsername(username);
                chatContentList.add(chatContent);
            }
        }
        chatContentListAdapter = new ChatContentListAdapter(context, chatContentList);
        chatView.setChatContentListAdapter(chatContentListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendmsg_chat_btn:
                String text = chatView.getChatText();
                Conversation conv = JMessageClient.getSingleConversation(title);
                if (null == conv) {
                    conv = Conversation.createSingleConversation(title);
                }
                TextContent textContent = new TextContent(text);
                Message message = conv.createSendMessage(textContent);

                JMessageClient.sendMessage(message);


                ChatContent chatContent = new ChatContent();
                chatContent.setChattype(ChatContent.TYPE.TO);

                long dateMs = new Date().getTime();
                TimeFormat timeFormat = new TimeFormat(context, dateMs);
                chatContent.setDateMs(dateMs);
                chatContent.setDate(timeFormat.getTime());
                chatContent.setChatText(text);
                chatContent.setUsername("wzy2");
                addChatcontent(chatContent);
                updateListView();
                break;
            case R.id.switchvoice_chat_imgb:

                if (isVoice) {
                    isVoice = false;
                    chatView.setVoiceSwitcherImg(false);
                } else {
                    ((ChatActivity)context).hideSoftInput();
                    isVoice = true;
                    chatView.setVoiceSwitcherImg(true);
                }

                break;

        }
    }

    //录音完成后的回调
    @Override
    public void onFinish(float seconds, String filePath) {
        ChatContent chatContent = new ChatContent();
        chatContent.setChattype(ChatContent.TYPE.VOICE_TO);
        chatContent.setVoiceFilePath(filePath);
        chatContent.setVoiceTime((int) seconds);
        long dateMs = new Date().getTime();
        TimeFormat timeFormat = new TimeFormat(context, dateMs);
        chatContent.setDateMs(dateMs);
        chatContent.setDate(timeFormat.getTime());
        chatContent.setUsername("wzy2");


        Conversation conv = JMessageClient.getSingleConversation(title);
        try {
            VoiceContent voiceContent = new VoiceContent(new File(filePath), (int) seconds);
            Message message = conv.createSendMessage(voiceContent);
            JMessageClient.sendMessage(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        chatContentList.add(chatContent);

        updateListView();
    }

    public void addChatcontent(ChatContent chatContent) {
        chatContentListAdapter.addContent(chatContent);
    }

    public void updateListView() {
        chatContentListAdapter.notifyDataSetChanged();
        chatView.getListView().setSelection(chatContentListAdapter.getCount());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("Item!");
        if (chatContentList.get(position).getChattype() == ChatContent.TYPE.VOICE_TO) {
            View aniView = view.findViewById(R.id.chatAudio_chatto_img);
            aniView.setBackgroundResource(R.drawable.voice_send);
            AnimationDrawable ani = (AnimationDrawable) aniView.getBackground();
            ani.start();
        } else if (chatContentList.get(position).getChattype() == ChatContent.TYPE.VOICE_FROM) {
            View aniView = view.findViewById(R.id.chatAudio_chatfrom_bg);
            aniView.setBackgroundResource(R.drawable.voice_receive);
            AnimationDrawable ani = (AnimationDrawable) aniView.getBackground();
            ani.start();
        }
    }
}
