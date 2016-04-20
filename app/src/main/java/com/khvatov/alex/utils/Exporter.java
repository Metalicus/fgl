package com.khvatov.alex.utils;

import android.content.Context;
import android.util.Log;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.entity.Game;
import com.khvatov.alex.entity.Platform;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Temporary exporter of data from app database
 */
public class Exporter {

    /**
     * Export data from platform and game tables to console
     */
    public static void exportToConsole(Context context) {
        try (final DbAdapter dbAdapter = new DbAdapter(context)) {
            dbAdapter.open();

            final List<Platform> platforms = dbAdapter.getPlatforms();
            JSONObject jsonObject;
            for (Platform platform : platforms) {
                jsonObject = new JSONObject();
                jsonObject.put("id", platform.getId());
                jsonObject.put("name", platform.getName());

                Log.d("FGL_DUMP_PLATFORM", jsonObject.toString());
            }

            final List<Game> games = dbAdapter.getGames();
            for (Game game : games) {
                jsonObject = new JSONObject();
                jsonObject.put("id", game.getId());
                jsonObject.put("name", game.getName());
                jsonObject.put("finishedDate", game.getFinishedDate() == null ? "null" : game.getFinishedDate().getTime());
                jsonObject.put("platform_id", game.getPlatform().getId());

                Log.d("FGL_DUMP_GAME", jsonObject.toString());
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
