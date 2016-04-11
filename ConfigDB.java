package com.gustavovalderrama.diccionario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gusta on 07/04/2016.
 */
public class ConfigDB extends SQLiteOpenHelper {
    public ConfigDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE palabras (idPalabra INT PRIMARY KEY, palabra text, traduccion text, idioma INT); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
