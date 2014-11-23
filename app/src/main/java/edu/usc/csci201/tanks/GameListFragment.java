package edu.usc.csci201.tanks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.faizmalkani.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.usc.csci201.tanks.network.TanksApi;
import edu.usc.csci201.tanks.network.responses.Game;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vmagro on 11/23/14.
 */
public class GameListFragment extends Fragment implements Callback<List<Game>>, View.OnClickListener {

    private static final String TAG = "GameListFragment";
    private ListView list;
    private FloatingActionButton newGameFab;

    private GameListFragmentListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (GameListFragmentListener) activity;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Activity for GameListFragment must implement GameListFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_list_fragment, null);
        list = (ListView) view.findViewById(R.id.game_list);
        newGameFab = (FloatingActionButton) view.findViewById(R.id.new_game_fab);
        newGameFab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reload();
    }

    @Override
    public void onResume() {
        super.onResume();

        reload();
    }

    private void reload() {
        TanksApi.TanksApi.listGames(this);
    }

    @Override
    public void success(List<Game> games, Response response) {
        list.setAdapter(new GameListAdapter(games));
    }

    @Override
    public void failure(RetrofitError error) {

    }

    @Override
    public void onClick(View view) {
        if (view.equals(newGameFab)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("New game");

            View dialogView = View.inflate(getActivity(), R.layout.new_game_dialog, null);
            final EditText nameEditText = (EditText) dialogView.findViewById(R.id.name);
            builder.setView(dialogView);

            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "create game selected");
                    TanksApi.TanksApi.createGame(nameEditText.getText().toString(), new Callback<Game>() {
                        @Override
                        public void success(Game game, Response response) {
                            reload();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), "Failed to create game", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        } else if (view.getTag() != null && view.getTag() instanceof Game) {
            listener.shouldJoinGame((Game) view.getTag());
        }
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
            return games.get(i).getId().hashCode();
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
            holder.joinButton.setTag(game);
            holder.joinButton.setOnClickListener(GameListFragment.this);

            return view;
        }

        private class ViewHolder {

            public TextView name;
            public TextView players;
            public Button joinButton;

        }
    }

    public static interface GameListFragmentListener {
        public void shouldJoinGame(Game game);
    }

}
