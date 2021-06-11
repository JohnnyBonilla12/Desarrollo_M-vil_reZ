package com.example.re_z.Activities.Recetas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.re_z.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Activity_Detalles extends AppCompatActivity {
    private ImageView imagen;
    private TextView id, titulo, dificultad, porciones, duracion, ingredientes, preparacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_receta_detalles);

        imagen = findViewById(R.id.detail_imagen);
        id = (TextView) findViewById(R.id.detail_id);
        titulo = (TextView) findViewById(R.id.detail_titulo);
        dificultad = (TextView) findViewById(R.id.detail_dificultad);
        porciones = (TextView) findViewById(R.id.detail_porciones);
        duracion = (TextView) findViewById(R.id.detail_duracion);
        ingredientes = (TextView) findViewById(R.id.detail_ingredientes);
        preparacion = (TextView) findViewById(R.id.detail_preparacion);

        loadImageFromStorage(getIntent().getStringExtra("imagen"));
        id.setText(getIntent().getStringExtra("id"));
        titulo.setText(getIntent().getStringExtra("titulo"));
        dificultad.setText(getIntent().getStringExtra("dificultad"));
        porciones.setText(getIntent().getStringExtra("porciones"));
        duracion.setText(getIntent().getStringExtra("duracion"));
        ingredientes.setText(getIntent().getStringExtra("ingredientes"));
        preparacion.setText(getIntent().getStringExtra("preparacion"));
    }

    private void loadImageFromStorage(String path) {
        try {
            File f = new File(path, "imagen.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            imagen.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
