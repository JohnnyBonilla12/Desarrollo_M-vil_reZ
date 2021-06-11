package com.example.re_z.Activities.Recetas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Activity_Editar extends AppCompatActivity {
    private ImageView imagen;
    private EditText titulo, dificultad, porciones, duracion, ingredientes, preparacion;
    private String Imagen, Id, Titulo, Dificultad, Porciones, Duracion, Ingredientes, Preparacion, Email;
    private Button button, buttonSelect;
    private Boolean valid = true;
    ProgressDialog progressDialog;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_receta_editar);

        imagen = (ImageView) findViewById(R.id.edit_image);
        titulo = (EditText) findViewById(R.id.edit_titulo);
        dificultad = (EditText) findViewById(R.id.edit_dificultad);
        porciones = (EditText) findViewById(R.id.edit_porciones);
        duracion = (EditText) findViewById(R.id.edit_duracion);
        ingredientes = (EditText) findViewById(R.id.edit_ingredientes);
        preparacion = (EditText) findViewById(R.id.edit_preparacion);
        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.edit_button);
        buttonSelect = (Button) findViewById(R.id.edit_selectImage);

        loadImageFromStorage(getIntent().getStringExtra("imagen"));
        Id = getIntent().getStringExtra("id");
        titulo.setText(getIntent().getStringExtra("titulo"));
        dificultad.setText(getIntent().getStringExtra("dificultad"));
        porciones.setText(getIntent().getStringExtra("porciones"));
        duracion.setText(getIntent().getStringExtra("duracion"));
        ingredientes.setText(getIntent().getStringExtra("ingredientes"));
        preparacion.setText(getIntent().getStringExtra("preparacion"));
        Email = getIntent().getStringExtra("correo");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap != null){
                    Imagen = getStringImage(bitmap);
                } else {
                    Bitmap actual = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
                    Imagen = getStringImage(actual);
                }
                Titulo = titulo.getText().toString();
                Dificultad = dificultad.getText().toString();
                Porciones = porciones.getText().toString();
                Duracion = duracion.getText().toString();
                Ingredientes = ingredientes.getText().toString();
                Preparacion = preparacion.getText().toString();

                comprobarCampos();

                if (valid) {
                    progressDialog.setMessage("Cargando");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_UPDATE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //System.out.println(response);
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(Activity_Editar.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if (jsonObject.getString("message").equals("Receta Editada")) {
                                    Fragment_Mis_Recetas.ma.refresh_list();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(Activity_Editar.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", Id);
                            params.put("imagen", Imagen);
                            params.put("titulo", Titulo);
                            params.put("dificultad", Dificultad);
                            params.put("porciones", Porciones);
                            params.put("duracion", Duracion);
                            params.put("ingredientes", Ingredientes);
                            params.put("preparacion", Preparacion);
                            params.put("email", Email);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(Activity_Editar.this).addToRequestQueue(stringRequest);
                }
            }
        });

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
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
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void comprobarCampos(){
        if (TextUtils.isEmpty(Titulo)) {
            titulo.setError("Ingresar Titulo");
            valid = false;
        } else {
            valid = true;

            if (TextUtils.isEmpty(Dificultad)) {
                dificultad.setError("Ingresar Dificultad");
                valid = false;
            } else {
                valid = true;

                if (TextUtils.isEmpty(Porciones)) {
                    porciones.setError("Ingresar Porciones");
                    valid = false;
                } else {
                    valid = true;

                    if (TextUtils.isEmpty(Duracion)) {
                        duracion.setError("Ingresar Duracion");
                        valid = false;
                    } else {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
