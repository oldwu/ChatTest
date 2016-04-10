package com.test.tools;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;

/**
 * Created by wzy on 16-4-10.
 */
public class ContentManager {

    private static ContentManager mContentManager = new ContentManager();

    private ContentManager(){

    }

    public static ContentManager getInstance(){
        return mContentManager;
    }

    public boolean isTextContent(Object o){
        if (TextContent.class.isInstance(o))
            return true;
        return false;
    }

    public boolean isVoiceContent(Object o){
        if(VoiceContent.class.isInstance(o))
            return true;
        return false;
    }

}
