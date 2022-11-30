package com.example.tarearecyclerv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class BDLite extends SQLiteOpenHelper {
    String create = "Create table aliments(id integer primary key autoincrement, nombre text, imagen integer)";
    public BDLite(@Nullable Context context, @Nullable String name, @Nullable
            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("Drop table if exists alimento");
        db.execSQL(create);
    }
}
