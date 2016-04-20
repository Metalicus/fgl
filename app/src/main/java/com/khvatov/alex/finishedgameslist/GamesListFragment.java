package com.khvatov.alex.finishedgameslist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.adapter.GameListAdapter;

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
}
