package com.test.beans;

import android.graphics.Bitmap;

/**
 * Created by wzy on 2016/3/31.
 */
public class ChatContent {

    public enum TYPE {
        FROM, TO, VOICE_FROM, VOICE_TO
    }

    private Bitmap headIcon;
    private String chatText;
    private String date;
    private long dateMs;
    private boolean timeVisable;
    private String voiceFilePath;
    private int voiceTime;

    public String username;
    public TYPE chattype;


    public boolean isTimeVisable() {
        return timeVisable;
    }

    public void setTimeVisable(boolean timeisVisable) {
        this.timeVisable = timeisVisable;
    }

    public long getDateMs() {
        return dateMs;
    }

    public void setDateMs(long dateMs) {
        this.dateMs = dateMs;
    }


    public TYPE getChattype() {
        return chattype;
    }

    public void setChattype(TYPE chattype) {
        this.chattype = chattype;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(Bitmap headIcon) {
        this.headIcon = headIcon;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVoiceFilePath() {
        return voiceFilePath;
    }

    public void setVoiceFilePath(String voiceFilePath) {
        this.voiceFilePath = voiceFilePath;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }
}
