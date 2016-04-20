package com.khvatov.alex.finishedgameslist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.adapter.PlatformListAdapter;

public class PlatformListFragment extends ListFragment {

    private PlatformListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try (final DbAdapter dbAdapter = new DbAdapter(getActivity().getBaseContext())) {
            dbAdapter.open();
            adapter = new PlatformListAdapter(getActivity(), dbAdapter.getPlatforms());
        }

        setListAdapter(adapter);
    }
}
