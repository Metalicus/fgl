package com.khvatov.alex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khvatov.alex.entity.Platform;
import com.khvatov.alex.finishedgameslist.R;

import java.util.List;

/**
 * Adapter for Platform list
 */
public class PlatformListAdapter extends ArrayAdapter<Platform> {

    public PlatformListAdapter(Context context, List<Platform> platforms) {
        super(context, 0, platforms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Platform platform = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.console_row, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.consoleRowItemTitle);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(platform.getName());

        return convertView;
    }

    private static class ViewHolder {
        private TextView name;
    }
}
