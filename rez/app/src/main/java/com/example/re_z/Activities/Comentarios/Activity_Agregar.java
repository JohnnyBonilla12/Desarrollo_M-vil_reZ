package com.example.re_z.Activities.Comentarios;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.re_z.Clases.Configuration;
import com.example.re_z.Clases.RequestHandler;
import com.example.re_z.Fragments.Access.Fragment_Mis_Comentarios;
import com.example.re_z.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Agregar extends AppCompatActivity {
    private EditText editComentario;
    private String Id, receta, comentario, correo;
    private Button buttonConfirmar;
    private Boolean valid = true;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_comentario_agregar);

        editComentario = (EditText) findViewById(R.id.editTextComentario);
        progressDialog = new ProgressDialog(this);
        buttonConfirmar = (Button) findViewById(R.id.confirmarAgregarComentario);

        Id = getIntent().getStringExtra("id");
        correo = getIntent().getStringExtra("correo");

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comentario = editComentario.getText().toString();

                comprobarCampos();

                if (valid) {
                    progressDialog.setMessage("Cargando");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_ADD_COMENTARIOS, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //System.out.println(response);
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(com.example.re_z.Activities.Comentarios.Activity_Agregar.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if (jsonObject.getString("message").equals("Comentario Editado")) {
                                    Fragment_Mis_Comentarios.ma.refresh_list();
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
                            Toast.makeText(com.example.re_z.Activities.Comentarios.Activity_Agregar.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", Id);
                            params.put("correo", correo);
                            params.put("comentario", comentario);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(com.example.re_z.Activities.Comentarios.Activity_Agregar.this).addToRequestQueue(stringRequest);
                }
            }
        });

    }


    public void comprobarCampos(){
        if (TextUtils.isEmpty(comentario)) {
            editComentario.setError("Ingresar Comentario");
            valid = false;
        } else {
            valid = true;
        }
    }

}
