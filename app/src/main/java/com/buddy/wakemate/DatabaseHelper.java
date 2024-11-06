package com.buddy.wakemate;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AlarmDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ALARMS = "alarms";

    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_TONE = "tone";
    private static final String KEY_ENABLED = "enabled";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ALARMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TIME + " INTEGER,"
                + KEY_TONE + " TEXT,"
                + KEY_ENABLED + " INTEGER" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
        onCreate(db);
    }

    public void addAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, alarm.getId());
        values.put(KEY_TIME, alarm.getTimeInMillis());
        values.put(KEY_TONE, alarm.getTone());
        values.put(KEY_ENABLED, alarm.isEnabled() ? 1 : 0);

        db.insert(TABLE_ALARMS, null, values);
        db.close();
    }

    public ArrayList<Alarm> getAllAlarms() {
        ArrayList<Alarm> alarms = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ALARMS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getInt(3) == 1
                );
                alarms.add(alarm);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return alarms;
    }
}
