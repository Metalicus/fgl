package com.khvatov.alex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khvatov.alex.entity.Game;
import com.khvatov.alex.finishedgameslist.R;
import com.khvatov.alex.utils.Const;

import java.util.List;

/**
 * Adapter for Game list
 */
public class GameListAdapter extends ArrayAdapter<Game> {

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

        final String unknownDate = getContext().getString(R.string.unknown_date);

        viewHolder.name.setText(game.getName());
        viewHolder.platform.setText(game.getPlatform().getName());
        viewHolder.date.setText(game.getFinishedDate() == null ? unknownDate : Const.DATE_FORMAT.format(game.getFinishedDate()));

        return convertView;
    }

    private static class ViewHolder {
        private TextView name;
        private TextView platform;
        private TextView date;
    }
}
