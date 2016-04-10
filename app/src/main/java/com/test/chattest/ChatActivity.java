package com.test.chattest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.test.adapter.ChatContentListAdapter;
import com.test.beans.ChatContent;
import com.test.controller.ChatController;
import com.test.tools.ContentManager;
import com.test.tools.TimeFormat;
import com.test.view.ChatView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by wzy on 2016/3/29.
 */
public class ChatActivity extends Activity {


    private ChatView chatView;
    private ChatController chatController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        chatView = (ChatView) findViewById(R.id.chatview);
        chatView.initModule();
        String title = getIntent().getStringExtra("username");
        chatView.setTitle(title);
        chatController = new ChatController(this, chatView, title);
        chatView.setOnClickListener(chatController);
        chatView.setAudioFinishRecorderListener(chatController);

        JMessageClient.registerEventReceiver(this);
    }


    @Override
    public void onBackPressed() {

        Intent intent = getIntent();
        setResult(2, intent);
        super.onBackPressed();

    }

    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();
        new LoadDatasTask(msg).execute();
    }

    class LoadDatasTask extends AsyncTask<Integer, Void, Integer> {



        private Message msg;
        private String username;
        private String chatcontent;
        private boolean isVoice;
        private String date;
        private String voiceFilePath;
        private int voiceSecond;

        public LoadDatasTask(Message msg) {
            this.msg = msg;
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            MessageContent content = msg.getContent();
            if (ContentManager.getInstance().isTextContent(content)) {
                TextContent textContent = (TextContent) content;
                chatcontent = textContent.getText();
                isVoice = false;
            }else{
                VoiceContent voiceContent = (VoiceContent)content;
                voiceFilePath = voiceContent.getLocalPath();
                voiceSecond = voiceContent.getDuration();
                isVoice = true;

            }
            UserInfo userInfo = (UserInfo) msg.getTargetInfo();
            username = userInfo.getUserName();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeFormat timeFormat = new TimeFormat(ChatActivity.this, new Date().getTime());
            date = timeFormat.getTime();
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            ChatContent chatContent = new ChatContent();
            chatContent.setUsername(username);
            chatContent.setDate(date);
            if (!isVoice) {

                chatContent.setChatText(chatcontent);
                chatContent.setChattype(ChatContent.TYPE.FROM);
            }else{
                chatContent.setVoiceFilePath(voiceFilePath);
                chatContent.setVoiceTime(voiceSecond);
                chatContent.setChattype(ChatContent.TYPE.VOICE_FROM);
            }
            chatController.addChatcontent(chatContent);
            chatController.updateListView();

        }
    }

    public void hideSoftInput(){
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
