package edu.usc.csci201.tanks;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.usc.csci201.tanks.network.TanksApi;
import edu.usc.csci201.tanks.network.responses.Game;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vmagro on 11/23/14.
 */
public class GameListFragment extends ListFragment implements Callback<List<Game>> {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TanksApi.TanksApi.listGames(this);
    }

    @Override
    public void success(List<Game> games, Response response) {
        setListAdapter(new GameListAdapter(games));
    }

    @Override
    public void failure(RetrofitError error) {

    }

    private class GameListAdapter extends BaseAdapter {
        private List<Game> games;

        public GameListAdapter(List<Game> games) {
            this.games = games;
        }

        @Override
        public int getCount() {
            return games.size();
        }

        @Override
        public Object getItem(int i) {
            return games.get(i);
        }

        @Override
        public long getItemId(int i) {
            return games.get(i).getId();
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup) {
            View view = null;
            ViewHolder holder = null;
            if (convertView != null && convertView.getTag() instanceof ViewHolder) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(getActivity(), R.layout.game_list_item, null);
                holder = new ViewHolder();
                holder.name = (TextView) view.findViewById(R.id.game_name);
                holder.players = (TextView) view.findViewById(R.id.game_players);
                holder.joinButton = (Button) view.findViewById(R.id.join_game_btn);
            }

            Game game = games.get(pos);
            holder.name.setText(game.getName());
            StringBuilder playersText = new StringBuilder();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                playersText.append(game.getPlayers().get(i));
                if (i < game.getPlayers().size() - 1) {
                    playersText.append(", ");
                }
            }
            holder.players.setText(playersText.toString());

            return view;
        }

        private class ViewHolder {

            public TextView name;
            public TextView players;
            public Button joinButton;

        }
    }
}
