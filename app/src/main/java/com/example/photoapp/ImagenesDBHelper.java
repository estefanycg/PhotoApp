package com.example.photoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.photoapp.ImagenesContract;
import android.database.sqlite.SQLiteOpenHelper;

public class ImagenesDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MiBaseDeDatos";

    public ImagenesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla en la base de datos


        String CREATE_TABLE = "CREATE TABLE img (id INTEGER PRIMARY KEY, name TEXT, description TEXT, imagen BLOB)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borrar la tabla y recrearla si hay una nueva versión de la base de datos
        if (oldVersion < 2) {
            // Agrega la columna "name"
            db.execSQL("ALTER TABLE img ADD COLUMN name TEXT");
            db.execSQL("ALTER TABLE img ADD COLUMN description TEXT");
        }

        db.execSQL("DROP TABLE IF EXISTS img");
        onCreate(db);
    }
}

