package com.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.adapter.ChatContentListAdapter;
import com.test.beans.ChatContent;
import com.test.chattest.R;
import com.test.controller.ChatController;

import java.util.List;

/**
 * Created by wzy on 2016/3/30.
 */
public class ChatView extends RelativeLayout {
    private Context context;

    private TextView username;

    private ImageButton voiceSwitcher;

    private EditText sendingText;

    private Button sendButton;

    private AudioRecordButton mAudioRecordButton;

    private ListView chatcontentListView;



    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void initModule() {
        username = (TextView) findViewById(R.id.username);
        voiceSwitcher = (ImageButton) findViewById(R.id.switchvoice_chat_imgb);
        sendingText = (EditText) findViewById(R.id.edit_chat_edt);
        sendButton = (Button) findViewById(R.id.sendmsg_chat_btn);
        chatcontentListView = (ListView) findViewById(R.id.content_chat_lst);
        mAudioRecordButton = (AudioRecordButton)findViewById(R.id.audiobutton_chat_btn);
    }



    public void setOnClickListener(ChatController chatController){
        sendButton.setOnClickListener(chatController);
        voiceSwitcher.setOnClickListener(chatController);
    }

    public void setAudioFinishRecorderListener(AudioRecordButton.AudioFinishRecorderListener listener){
        mAudioRecordButton.setAudioFinishRecorderListener(listener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        chatcontentListView.setOnItemClickListener(listener);
    }

    public String getChatText(){
        String word = sendingText.getText().toString();
        sendingText.setText("");
        return word;

    }

    public void setChatContentListAdapter(ListAdapter adapter){
        chatcontentListView.setAdapter(adapter);
    }

    public ListView getListView(){
        return chatcontentListView;
    }

    public void setTitle(String title){
        username.setText(title);
    }

    public void setVoiceSwitcherImg(boolean isVoice){
        if (isVoice){
            voiceSwitcher.setBackgroundResource(R.mipmap.keyboard);
            sendButton.setVisibility(GONE);
            sendingText.setVisibility(GONE);
            mAudioRecordButton.setVisibility(VISIBLE);
        }else{
            voiceSwitcher.setBackgroundResource(R.mipmap.voice);
            sendButton.setVisibility(VISIBLE);
            sendingText.setVisibility(VISIBLE);
            mAudioRecordButton.setVisibility(GONE);
        }
    }







}
