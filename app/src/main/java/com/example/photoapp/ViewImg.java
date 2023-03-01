package com.example.photoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class ViewImg extends AppCompatActivity {

    private ImageView mImageView;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_img);

        ImagenesDBHelper dbHelper = new ImagenesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        String selectedId = getIntent().getStringExtra("id");

        String[] columns = {"name,description,imagen"};
        String selection = "id=?";
        String[] selectionArgs = {selectedId};
        Cursor cursor = mDatabase.query("img", columns, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();


        byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("imagen"));
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));



        // Mostrar la imagen en el ImageView, datos extras
        mImageView = findViewById(R.id.imageView);
        mImageView.setImageBitmap(bitmap);


        // Mostrar el nombre y la descripción de la imagen en TextViews
        EditText idView = findViewById(R.id.txtID);
        EditText nameView = findViewById(R.id.txtNombre);
        EditText descriptionView = findViewById(R.id.txtDescripcion);

        idView.setText(selectedId);
        nameView.setText(name);
        descriptionView.setText(description);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos
        mDatabase.close();
    }
}