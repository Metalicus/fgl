package com.khvatov.alex.finishedgameslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.adapter.GameListAdapter;
import com.khvatov.alex.entity.Game;

public class GamesListFragment extends ListFragment {

    private GameListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try (final DbAdapter dbAdapter = new DbAdapter(getActivity().getBaseContext())) {
            dbAdapter.open();
            adapter = new GameListAdapter(getActivity(), dbAdapter.getGames());
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Game game = (Game) getListAdapter().getItem(position);

        final Intent intent = new Intent(getActivity(), GameDetailActivity.class);
        intent.putExtra(Game.ID, game.getId());
        intent.putExtra(Game.PLATFORM, game.getPlatform());
        intent.putExtra(Game.NAME, game.getName());
        intent.putExtra(Game.FINISHED_DATE, game.getFinishedDate());

        startActivity(intent);
    }
}
