package edu.usc.csci201.tanks.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.usc.csci201.tanks.GameState;
import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.R;

/**
 * Created by vmagro on 11/30/14.
 */
public class ChatActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_CHAT_NAME = "chat_name";

    public static String getTeamChatName() {
        List<PlayerInfo> playerInfos = GameState.getInstance().getPlayerInfos();
        List<String> playerIds = new ArrayList<String>(GameState.getInstance().getPlayerInfos().size() / 2 + 1);
        for (PlayerInfo playerInfo : GameState.getInstance().getPlayerInfos()) {
            if (playerInfo.isOnMyTeam())
                playerIds.add(playerInfo.getId());
        }
        //sort them so that the name will be symmetrical - I understand this now but maybe not in the future
        Collections.sort(playerIds);

        StringBuilder builder = new StringBuilder();
        for (String playerId : playerIds)
            builder.append(playerId);

        return builder.toString();
    }

    public static String getAllChatName() {
        List<String> playerIds = new ArrayList<String>(GameState.getInstance().getPlayerInfos().size());
        for (PlayerInfo playerInfo : GameState.getInstance().getPlayerInfos()) {
            playerIds.add(playerInfo.getId());
        }
        //sort them so that the name will be symmetrical - I understand this now but maybe not in the future
        Collections.sort(playerIds);

        StringBuilder builder = new StringBuilder();
        for (String playerId : playerIds)
            builder.append(playerId);

        return builder.toString();
    }

    public static String getPlayerChatName(String id1, String id2) {
        List<String> playerIds = new ArrayList<String>(2);
        playerIds.add(id1);
        playerIds.add(id2);

        //sort them so that the name will be symmetrical - I understand this now but maybe not in the future
        Collections.sort(playerIds);

        StringBuilder builder = new StringBuilder();
        for (String playerId : playerIds)
            builder.append(playerId);

        return builder.toString();
    }


    public static Intent getIntentForChat(Context context, String chatName) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CHAT_NAME, chatName);
        return intent;
    }

    private Firebase chatRef;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        String chatName = getIntent().getExtras().getString(EXTRA_CHAT_NAME, null);
        chatRef = new Firebase("https://csci-201-tanks.firebaseio.com/chats/" + chatName);

        ListView messagesList = (ListView) findViewById(R.id.message_list);
        messagesList.setAdapter(new ChatAdapter());

        editText = (EditText) findViewById(R.id.edit_text);

        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Firebase messageRef = chatRef.push();
        messageRef.setPriority(ServerValue.TIMESTAMP);

        Message message = new Message(GameState.getInstance().getMe().getImageUrl(), GameState.getInstance().getMe().getId(), editText.getText().toString());
        messageRef.setValue(message);

        editText.setText("");
    }

    private class ChatAdapter extends BaseAdapter implements ValueEventListener {

        private LayoutInflater inflater;
        private List<Message> messages = new LinkedList<Message>();

        public ChatAdapter() {
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            chatRef.addValueEventListener(this);
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int pos) {
            return messages.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return getItem(pos).hashCode();
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup) {
            View view;
            ViewHolder holder;
            if (convertView != null && convertView.getTag() instanceof ViewHolder) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = inflater.inflate(R.layout.message_list_item, null);
                holder = new ViewHolder();
                holder.senderImage = (ImageView) view.findViewById(R.id.sender_image);
                holder.text = (TextView) view.findViewById(R.id.message_text);
            }
            view.setTag(holder);

            Message message = messages.get(pos);

            Picasso.with(ChatActivity.this).load(message.getSenderImageUrl()).fit().transform(new CircleTransform()).into(holder.senderImage);
            holder.text.setText(message.getText());

            return view;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            messages.clear();
            for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                messages.add(messageSnapshot.getValue(Message.class));
            }
            notifyDataSetChanged();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }

        private class ViewHolder {
            public ImageView senderImage;
            public TextView text;
        }
    }
}
