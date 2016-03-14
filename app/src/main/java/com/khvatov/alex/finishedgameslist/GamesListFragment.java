package com.khvatov.alex.finishedgameslist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.khvatov.alex.adapter.GameListAdapter;
import com.khvatov.alex.entity.Game;
import com.khvatov.alex.entity.Platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GamesListFragment extends ListFragment {

    private GameListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Platform ps4 = new Platform("Playstation 4");
        final Platform pc = new Platform("PC");

        final List<Game> games = new ArrayList<>();
        games.add(new Game(ps4, "Diablo III", new Date()));
        games.add(new Game(pc, "Heroes of Might and Magic III", null));

        adapter = new GameListAdapter(getActivity(), games);
        setListAdapter(adapter);
    }
}
