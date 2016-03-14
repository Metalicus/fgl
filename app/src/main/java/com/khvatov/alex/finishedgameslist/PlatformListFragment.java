package com.khvatov.alex.finishedgameslist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.khvatov.alex.adapter.PlatformListAdapter;
import com.khvatov.alex.entity.Platform;

import java.util.ArrayList;
import java.util.List;

public class PlatformListFragment extends ListFragment {

    private PlatformListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<Platform> platforms = new ArrayList<>();
        platforms.add(new Platform("Playstation 3"));
        platforms.add(new Platform("Playstation 4"));
        platforms.add(new Platform("PSP"));
        platforms.add(new Platform("PSVita"));
        platforms.add(new Platform("Sega Mega Drive 2"));
        platforms.add(new Platform("Dendy"));

        adapter = new PlatformListAdapter(getActivity(), platforms);
        setListAdapter(adapter);
    }
}
