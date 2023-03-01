package com.example.photoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImagenesDBHelper dbHelper;

    Button btnCamara,btnGuardar,btnListaImagenes;
    TextView txtNombre, txtDescripcion;
    Context context ;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext() ;
        btnCamara = findViewById(R.id.btnCamara);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnListaImagenes = findViewById(R.id.btnListaImagenes);

        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtNombre.requestFocus();

        imagen = findViewById(R.id.imageView2);

        dbHelper = new ImagenesDBHelper(this);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        btnListaImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListImg.class);
                startActivity(intent);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtNombre.getText().toString();
                String description = txtDescripcion.getText().toString();
                // Obtener la imagen del ImageView
                Bitmap img = ((BitmapDrawable) imagen.getDrawable()).getBitmap();

                // Convertir la imagen a un array de bytes
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // Guardar el array de bytes en la base de datos
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("description", description);
                values.put("imagen", byteArray);
                long resultado = db.insert("img", null, values);



// Mostrar un mensaje si la imagen se guardó correctamente
                if (resultado != -1) {
                    Toast.makeText(context, "Los datos de han guardado.", Toast.LENGTH_SHORT).show();
                    limpiar();
                }else{
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void limpiar(){
        txtNombre.setText("");
        txtDescripcion.setText("");
        imagen.setImageDrawable(null);
        txtNombre.requestFocus();
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = null;
            try {
                image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Save a file: path for use with ACTION_VIEW intents
            String rutaImagen = image.getAbsolutePath();

            try (FileOutputStream out = new FileOutputStream(image)) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String nombreImagen = image.getName();
            String fecha = timeStamp;


            /*Imagen imagen = new Imagen(nombreImagen, rutaImagen, fecha);
            long newRowId = dbHelper.insertarImagen(imagen);*/
        }
    }
}
