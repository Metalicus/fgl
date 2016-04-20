package com.khvatov.alex.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.khvatov.alex.entity.Game;
import com.khvatov.alex.entity.Platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common DB adapter
 */
public class DbAdapter implements AutoCloseable {

    private static final String DATABASE_NAME = "finished_games_list.db";
    private static final int DATABASE_VERSION = 1;

    private static final String PLATFORM_TABLE = "platform";
    private static final String PLATFORM_COLUMN_ID = "id";
    private static final String PLATFORM_COLUMN_NAME = "name";
    private static final String PLATFORM_CREATE_TABLE = "create table " + PLATFORM_TABLE + " ("
            + PLATFORM_COLUMN_ID + " integer primary key autoincrement, "
            + PLATFORM_COLUMN_NAME + " text not null);";
    private final static String[] PLATFORM_COLUMNS = {PLATFORM_COLUMN_ID, PLATFORM_COLUMN_NAME};

    private static final String GAME_TABLE = "game";
    private static final String GAME_COLUMN_ID = "id";
    private static final String GAME_COLUMN_PLATFORM_ID = "platform_id";
    private static final String GAME_COLUMN_NAME = "name";
    private static final String GAME_COLUMN_DATE = "finished_date";
    private static final String GAME_CREATE_TABLE = "create table " + GAME_TABLE + " ("
            + GAME_COLUMN_ID + " integer primary key autoincrement, "
            + GAME_COLUMN_PLATFORM_ID + " integer not null, "
            + GAME_COLUMN_NAME + " text not null, "
            + GAME_COLUMN_DATE + " integer not null);";
    private final static String SELECT_ALL_GAMES = "SELECT " + GAME_TABLE + "." + GAME_COLUMN_ID + "," +
            GAME_TABLE + "." + GAME_COLUMN_NAME + "," +
            GAME_TABLE + "." + GAME_COLUMN_DATE + "," +
            PLATFORM_TABLE + "." + PLATFORM_COLUMN_ID + "," +
            PLATFORM_TABLE + "." + PLATFORM_COLUMN_NAME +
            " FROM " + GAME_TABLE +
            " INNER JOIN " + PLATFORM_TABLE + " ON " + GAME_TABLE + "." + GAME_COLUMN_PLATFORM_ID + "=" +
            PLATFORM_TABLE + "." + PLATFORM_COLUMN_ID;

    private SQLiteDatabase database;
    private Context context;
    private DbHelper dbHelper;

    public DbAdapter(Context context) {
        this.context = context;
    }

    public DbAdapter open() throws SQLException {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    /**
     * Create and insert new instance of Platform object
     *
     * @param name name of the new platform
     * @return created Platform
     * @see Platform
     */
    public Platform createPlatform(String name) {
        final ContentValues values = new ContentValues(1);
        values.put(PLATFORM_COLUMN_NAME, name);

        final long id = database.insert(PLATFORM_TABLE, null, values);

        return new Platform(id, name);
    }

    /**
     * Create and insert new instance of Game object
     *
     * @param name         name of the game
     * @param finishedDate date of finishing this game
     * @param platform     platform on which game was finished
     * @return created Game
     * @see Game
     */
    public Game createGame(String name, Date finishedDate, Platform platform) {
        final ContentValues values = new ContentValues(3);
        values.put(GAME_COLUMN_PLATFORM_ID, platform.getId());
        values.put(GAME_COLUMN_NAME, name);
        values.put(GAME_COLUMN_DATE, finishedDate.getTime());

        final long id = database.insert(GAME_TABLE, null, values);

        return new Game(id, platform, name, finishedDate);
    }

    /**
     * @return collection of all Platforms stored in database
     * @see Platform
     */
    public List<Platform> getPlatforms() {
        final List<Platform> platforms = new ArrayList<>();
        try (final Cursor cursor = database.query(PLATFORM_TABLE, PLATFORM_COLUMNS, null, null, null, null, null)) {
            for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious())
                platforms.add(new Platform(cursor.getLong(0), cursor.getString(1)));
        }
        return platforms;
    }

    /**
     * @return collection of all Games stored in database
     * @see Game
     */
    public List<Game> getGames() {
        final Map<Long, Platform> platformMap = new HashMap<>();
        final List<Game> games = new ArrayList<>();
        try (final Cursor cursor = database.rawQuery(SELECT_ALL_GAMES, null)) {
            Long platformId;
            Platform platform;
            for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
                platformId = cursor.getLong(3);
                if (!platformMap.containsKey(platformId)) {
                    platform = new Platform(platformId, cursor.getString(4));
                    platformMap.put(platformId, platform);
                } else
                    platform = platformMap.get(platformId);

                games.add(new Game(cursor.getLong(0), platform, cursor.getString(1), new Date(cursor.getLong(2))));
            }

        }
        return games;
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(PLATFORM_CREATE_TABLE);
            db.execSQL(GAME_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
