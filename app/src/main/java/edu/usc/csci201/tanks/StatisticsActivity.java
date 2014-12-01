package edu.usc.csci201.tanks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import edu.usc.csci201.tanks.chat.CircleTransform;

/**
 * Created by vmagro on 11/30/14.
 */
public class StatisticsActivity extends Activity {

    private static final String EXTRA_GAME_NAME = "game_name";

    public static Intent getStatisticsIntentForGame(Context context, String gameName) {
        Intent intent = new Intent(context, StatisticsActivity.class);
        intent.putExtra(EXTRA_GAME_NAME, gameName);
        return intent;
    }

    private Firebase statsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistics);

        statsRef = new Firebase("https://csci-201-tanks.firebaseio.com/stats/" + getIntent().getStringExtra(EXTRA_GAME_NAME));

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new StatisticsAdapter());
    }

    private class StatisticsAdapter extends BaseAdapter implements ValueEventListener {

        List<Statistic> statistics = new LinkedList<Statistic>();
        LayoutInflater inflater;

        public StatisticsAdapter() {
            statsRef.addValueEventListener(this);
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return statistics.size();
        }

        @Override
        public Object getItem(int i) {
            return statistics.get(i);
        }

        @Override
        public long getItemId(int i) {
            return statistics.get(i).hashCode();
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup) {
            View view;
            ViewHolder holder;
            if (convertView != null && convertView.getTag() instanceof ViewHolder) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = inflater.inflate(R.layout.stats_row_entry, null);
                holder = new ViewHolder();
                holder.userImage = (ImageView) view.findViewById(R.id.user_image);
                holder.hitCount = (TextView) view.findViewById(R.id.hit_count);
                holder.killCount = (TextView) view.findViewById(R.id.kill_count);
                holder.score = (TextView) view.findViewById(R.id.score);
            }
            view.setTag(holder);

            Statistic statistic = statistics.get(pos);

            Picasso.with(StatisticsActivity.this).load(GameState.getInstance().getPlayer(statistic.playerId).getImageUrl()).fit().transform(new CircleTransform()).into(holder.userImage);
            holder.hitCount.setText(statistic.hits);
            holder.killCount.setText(statistic.kills);
            holder.score.setText(statistic.hits + statistic.kills);

            return view;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            statistics.clear();
            for (DataSnapshot statSnapshot : dataSnapshot.getChildren()) {
                statistics.add(statSnapshot.getValue(Statistic.class));
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }

        private class ViewHolder {

            public ImageView userImage;
            public TextView hitCount;
            public TextView killCount;
            public TextView score;

        }
    }
}
