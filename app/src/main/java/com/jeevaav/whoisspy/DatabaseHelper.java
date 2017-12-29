package com.jeevaav.whoisspy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "words.db";
    private static final String TABLE_NAME = "words_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "normal_word";
    private static final String COL3 = "spy_word";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 COL2 + " TEXT, " + COL3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getDatabaseData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id.toString(), null);
        return result;
    }
}
