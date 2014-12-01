package edu.usc.csci201.tanks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

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

    private Firebase statisticsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new StatisticsAdapter());
    }

    private class StatisticsAdapter extends BaseAdapter implements ValueEventListener {

        List<Statistic> statistics = new LinkedList<Statistic>();

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
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
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
    }
}
