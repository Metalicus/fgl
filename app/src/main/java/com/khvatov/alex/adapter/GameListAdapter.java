package com.khvatov.alex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khvatov.alex.entity.Game;
import com.khvatov.alex.finishedgameslist.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for Game list
 */
public class GameListAdapter extends ArrayAdapter<Game> {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public GameListAdapter(Context context, List<Game> games) {
        super(context, 0, games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Game game = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_row, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.gameRowItemTitle);
            viewHolder.platform = (TextView) convertView.findViewById(R.id.gameRowPlatformName);
            viewHolder.date = (TextView) convertView.findViewById(R.id.gameRowFinishedDate);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(game.getName());
        viewHolder.platform.setText(game.getPlatform().getName());
        viewHolder.date.setText(game.getFinishedDate() == null ? "" : DATE_FORMAT.format(game.getFinishedDate()));

        return convertView;

    }

    private static class ViewHolder {
        private TextView name;
        private TextView platform;
        private TextView date;
    }
}
