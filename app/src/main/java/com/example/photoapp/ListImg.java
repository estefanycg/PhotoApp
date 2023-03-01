package com.example.photoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListImg extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<String> mIdsList;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_img);

        ImagenesDBHelper dbHelper = new ImagenesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        String[] columns = {"id"};
        Cursor cursor = mDatabase.query("img", columns, null, null, null, null, null);

        mIdsList = new ArrayList<String>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            mIdsList.add(id);
        }

        mListView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mIdsList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = mIdsList.get(position);
                Intent intent = new Intent(ListImg.this, ViewImg.class);
                intent.putExtra("id", selectedId);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos
        mDatabase.close();
    }
}