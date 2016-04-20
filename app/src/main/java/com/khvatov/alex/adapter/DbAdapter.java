package com.khvatov.alex.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.khvatov.alex.entity.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Common DB adapter
 */
public class DbAdapter implements AutoCloseable {

    private static final String DATABASE_NAME = "finished_games_list.db";
    private static final int DATABASE_VERSION = 1;

    private static final String PLATFORM_TABLE = "platform";
    private static final String PLATFORM_COLUMN_ID = "ID";
    private static final String PLATFORM_COLUMN_NAME = "name";
    private static final String PLATFORM_CREATE_TABLE = "create table " + PLATFORM_TABLE + " ("
            + PLATFORM_COLUMN_ID + " integer primary key autoincrement, "
            + PLATFORM_COLUMN_NAME + " text not null);";
    private final static String[] PLATFORM_COLUMNS = {PLATFORM_COLUMN_ID, PLATFORM_COLUMN_NAME};

    private static final String GAME_TABLE = "game";
    private static final String GAME_COLUMN_ID = "ID";

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
     * @see com.khvatov.alex.entity.Platform
     */
    public Platform createPlatform(String name) {
        final ContentValues values = new ContentValues(1);
        values.put(PLATFORM_COLUMN_NAME, name);

        final long id = database.insert(PLATFORM_TABLE, null, values);

        return new Platform(id, name);
    }

    /**
     * @return get collection of all Platforms stored in database
     * @see com.khvatov.alex.entity.Platform
     */
    public List<Platform> getPlatforms() {
        final List<Platform> platforms = new ArrayList<>();
        try (final Cursor cursor = database.query(PLATFORM_TABLE, PLATFORM_COLUMNS, null, null, null, null, null)) {
            for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious())
                platforms.add(cursorToPlatform(cursor));
        }
        return platforms;
    }

    private Platform cursorToPlatform(Cursor cursor) {
        return new Platform(cursor.getLong(0), cursor.getString(1));
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create Platform table
            db.execSQL(PLATFORM_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
