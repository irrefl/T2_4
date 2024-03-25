package com.irvinflores.tarea2_4.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataIgniter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String Database = "photographs.db";
    public static final String TABLE = "photographs";

    public DataIgniter(@Nullable Context context) {
        super(context, Database, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE)
                .append("(")
                .append("id INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append("description TEXT NOT NULL,")
                .append("image BLOB")
                .append(")")
                .toString();

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE);
        onCreate(sqLiteDatabase);

    }
}
