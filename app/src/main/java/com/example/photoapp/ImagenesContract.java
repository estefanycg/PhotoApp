package com.example.photoapp;

import android.provider.BaseColumns;

public final class ImagenesContract {
    private ImagenesContract() {}

    public static class ImagenEntry implements BaseColumns {
        public static final String TABLE_NAME = "imagenes";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_RUTA = "ruta";
        public static final String COLUMN_FECHA = "fecha";
    }
}
