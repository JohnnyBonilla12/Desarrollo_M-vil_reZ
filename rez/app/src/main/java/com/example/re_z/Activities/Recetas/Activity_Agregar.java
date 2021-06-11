package com.example.re_z.Activities.Recetas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.re_z.Clases.Configuration;
import com.example.re_z.Clases.RequestHandler;
import com.example.re_z.Fragments.Access.Fragment_Mis_Recetas;
import com.example.re_z.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Activity_Agregar extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private EditText titulo, dificultad, porciones, duracion, ingredientes, preparacion;
    private String Imagen, Titulo, Dificultad, Porciones, Duracion, Ingredientes, Preparacion;
    private Button button, btnSelect;
    private Boolean valid = true;
    private ProgressDialog progressDialog;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_receta_agregar);

        imageView = (ImageView) findViewById(R.id.add_imagen);
        titulo = (EditText) findViewById(R.id.add_titulo);
        dificultad = (EditText) findViewById(R.id.add_dificultad);
        porciones = (EditText) findViewById(R.id.add_porciones);
        duracion = (EditText) findViewById(R.id.add_duracion);
        ingredientes = (EditText) findViewById(R.id.add_ingredientes);
        preparacion = (EditText) findViewById(R.id.add_preparacion);

        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.add_button);
        btnSelect = (Button) findViewById(R.id.add_btnSelect);

        button.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
    }

    public void alta(){
        Titulo = titulo.getText().toString();
        Dificultad = dificultad.getText().toString();
        Porciones = porciones.getText().toString();
        Duracion = duracion.getText().toString();
        Ingredientes = ingredientes.getText().toString();
        Preparacion = preparacion.getText().toString();

        this.comprobarCampos();

        if(valid){
            if(bitmap != null){
                Imagen = getStringImage(bitmap);
            } else {
                Imagen = "";
            }
            progressDialog.setMessage("Cargando");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_ADD, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(Activity_Agregar.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        if(jsonObject.getString("message").equals("Receta Agregada")){
                            Fragment_Mis_Recetas.ma.refresh_list();
                            finish();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(Activity_Agregar.this, "Error en el Servidor",Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String , String> getParams() throws AuthFailureError {
                    Map<String , String> params = new HashMap<>();
                    params.put("imagen", Imagen);
                    params.put("titulo", Titulo);
                    params.put("dificultad", Dificultad);
                    params.put("porciones", Porciones);
                    params.put("duracion", Duracion);
                    params.put("ingredientes", Ingredientes);
                    params.put("preparacion", Preparacion);
                    params.put("email", getIntent().getStringExtra("correo"));
                    return params;
                }
            };
            RequestHandler.getInstance(Activity_Agregar.this).addToRequestQueue(stringRequest);
        }
    }

    private void comprobarCampos(){
        if(TextUtils.isEmpty(Titulo)){
            titulo.setError("Ingresar Titulo");
            valid = false;
        }else {
            valid = true;

            if(TextUtils.isEmpty(Dificultad)){
                dificultad.setError("Ingresar Dificultad");
                valid = false;
            }else {
                valid = true;

                if(TextUtils.isEmpty(Porciones)){
                    porciones.setError("Ingresar Porciones");
                    valid = false;
                }else {
                    valid = true;

                    if(TextUtils.isEmpty(Duracion)){
                        duracion.setError("Ingresar Duracion");
                        valid = false;
                    }else {
                        valid = true;

                        if(TextUtils.isEmpty(Ingredientes)){
                            ingredientes.setError("Ingresar Ingredientes");
                            valid = false;
                        } else {
                            valid = true;

                            if(TextUtils.isEmpty(Preparacion)){
                                preparacion.setError("Ingresar Preparacion");
                                valid = false;
                            } else {
                                valid = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            alta();
        }
        if(v == btnSelect){
            System.out.println("Hola");
            showFileChooser();
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