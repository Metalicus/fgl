package com.khvatov.alex.finishedgameslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.adapter.PlatformListAdapter;
import com.khvatov.alex.entity.Platform;

public class PlatformListFragment extends ListFragment {

    private PlatformListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try (final DbAdapter dbAdapter = new DbAdapter(getActivity().getBaseContext())) {
            dbAdapter.open();
            adapter = new PlatformListAdapter(getActivity(), dbAdapter.getPlatforms());
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Platform platform = (Platform) getListAdapter().getItem(position);

        final Intent intent = new Intent(getActivity(), PlatformDetailActivity.class);
        intent.putExtra(Platform.ID, platform.getId());
        intent.putExtra(Platform.NAME, platform.getName());

        startActivity(intent);
    }
}
