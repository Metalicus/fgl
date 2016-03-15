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
        final Platform psp = new Platform("PSP");

        final List<Game> games = new ArrayList<>();
        games.add(new Game(ps4, "Diablo III", new Date()));
        games.add(new Game(pc, "Heroes of Might and Magic III", null));
        games.add(new Game(psp, "GTA Libery City Stories", null));
        games.add(new Game(psp, "GTA Vice City Stories", null));
        games.add(new Game(psp, "Harry Potter and the Goblet of Fire", null));
        games.add(new Game(psp, "Need For Speed Underground Rivals", null));
        games.add(new Game(pc, "Flash Back", null));
        games.add(new Game(pc, "Civilization I", null));
        games.add(new Game(pc, "Corridor 7", null));
        games.add(new Game(pc, "Golden Axe I", null));
        games.add(new Game(pc, "Prince of Percia", null));
        games.add(new Game(pc, "Small Soldiers 2", null));
        games.add(new Game(pc, "Commandos: Behind the Enemy Lines", null));
        games.add(new Game(pc, "Commandos: Beyond the Call of Duty", null));
        games.add(new Game(pc, "Heroes of Might and Magic III: The Restoration of Erathia", null));
        games.add(new Game(pc, "Heroes of Might and Magic III: The Armageddon's Blade", null));
        games.add(new Game(pc, "Heroes of Might and Magic III: The Shadow of Death", null));
        games.add(new Game(pc, "Heroes of Might and Magic III: Chronicles (1-2)", null));
        games.add(new Game(pc, "U.F.O.", null));
        games.add(new Game(pc, "Half-Life", null));
        games.add(new Game(pc, "Half-Life: Opposinf Force", null));
        games.add(new Game(pc, "Half-Life: Blue Shift", null));
        games.add(new Game(pc, "Operation Flashpoint", null));
        games.add(new Game(pc, "No One Life Forever", null));
        games.add(new Game(pc, "No One Life Forever 2", null));
        games.add(new Game(pc, "Дальнобойщики: Путь к победе", null));
        games.add(new Game(pc, "Дальнобойщики 2", null));
        games.add(new Game(pc, "Hitman: Codename 47", null));
        games.add(new Game(pc, "Arcanum: Of Steamwork and Magic Obscura", null));
        games.add(new Game(pc, "Alien vs. Predator 2", null));
        games.add(new Game(pc, "Fallout", null));
        games.add(new Game(pc, "Fallout 2", null));
        games.add(new Game(pc, "Seriuos Sam", null));
        games.add(new Game(pc, "Serious Sam: Second Encounter", null));
        games.add(new Game(pc, "Mafia", null));
        games.add(new Game(pc, "Icewind Dale", null));
        games.add(new Game(pc, "Icewind Dale 2", null));
        games.add(new Game(pc, "Штырлиц", null));
        games.add(new Game(pc, "The Neverhood", null));

        adapter = new GameListAdapter(getActivity(), games);
        setListAdapter(adapter);
    }
}
