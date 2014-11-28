package edu.usc.csci201.tanks.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.usc.csci201.tanks.gameplay.Game;
import edu.usc.csci201.tanks.gameplay.Player;
import edu.usc.csci201.tanks.graphics.ChatInterfaceListener;

/**
 * Created by vmagro on 11/27/14.
 */
public class ChatListener implements ChatInterfaceListener {

    private Map<Player, WebView> playerWebViewMap = new HashMap<Player, WebView>();

    @Override
    public void init(final Context context, final Game game) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                for (Player player : game.getPlayers()) {
                    playerWebViewMap.put(player, createConferenceWebview(context));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    @Override
    public void userDidSelectChannel(ChatChannel channel) {
        switch (channel) {
            case TEAM:
                if (Build.VERSION.SDK_INT >= 19) {
                    for (WebView wv : playerWebViewMap.values()) {
                        wv.evaluateJavascript("mute()", null);
                    }
                    for (Player teammate : Player.me().getTeammates()) {
                        playerWebViewMap.get(teammate).evaluateJavascript("unmute()", null);
                    }
                }
                break;
            case ALL:
                if (Build.VERSION.SDK_INT >= 19) {
                    for (WebView wv : playerWebViewMap.values()) {
                        wv.evaluateJavascript("unmute()", null);
                    }
                }
                break;
        }
    }

    @Override
    public void userDidSelectPlayer(Player player) {
        if (Build.VERSION.SDK_INT >= 19) {
            for (WebView wv : playerWebViewMap.values()) {
                wv.evaluateJavascript("mute()", null);
            }
            playerWebViewMap.get(player).evaluateJavascript("unmute()", null);
        }
    }

    @Override
    public Bitmap getImageForPlayer(Context context, Player player) {
        try {
            return Picasso.with(context).load(player.getPhotoUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private WebView createConferenceWebview(Context context) {
        WebView webView = new WebView(context);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= 21) {
                    request.grant(new String[]{PermissionRequest.RESOURCE_AUDIO_CAPTURE, PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID, PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                }
            }
        });

        Conference conference = VoiceChatApi.VoiceChatApi.createConference();
        webView.loadUrl(conference.getConferenceUrl());

        return webView;
    }
}
