package edu.usc.csci201.tanks.chat;

import android.content.Context;
import android.webkit.WebView;

import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.graphics.ChatInterfaceListener;

/**
 * Created by vmagro on 11/30/14.
 */
public class Chat implements ChatInterfaceListener {

    private static Chat instance;

    public static Chat getInstance() {
        if (instance == null)
            instance = new Chat();
        return instance;
    }

    private Chat() {

    }

    private Context context;
    private WebView myConference;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setMyConference(String url) {
        myConference = new WebView(context);
        myConference.loadUrl(url);
    }

    @Override
    public void userDidSelectChannel(ChatChannel channel, PlayerInfo user) {

    }
}
