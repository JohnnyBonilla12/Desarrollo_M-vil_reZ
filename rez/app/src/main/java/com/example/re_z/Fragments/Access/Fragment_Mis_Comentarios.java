package com.example.re_z.Fragments.Access;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.re_z.Activities.Activity_Access;
import com.example.re_z.Activities.Comentarios.Activity_Agregar;
import com.example.re_z.Clases.Comentario;
import com.example.re_z.Clases.Configuration;
import com.example.re_z.Clases.Adaptador_Mis_Comentarios;
import com.example.re_z.Clases.RequestHandler;
import com.example.re_z.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Mis_Comentarios extends Fragment{
    public static Fragment_Mis_Comentarios ma;
    private List<Comentario> lista_Comentarios;
    private RecyclerView recyclerView;
    private Adaptador_Mis_Comentarios adapter;
    private ProgressDialog progressDialog;
    private String correo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.access_fragment_mis_comentarios, container, false);
        recyclerView = root.findViewById(R.id.mis_comentarios_lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());
        lista_Comentarios = new ArrayList<>();
        ma = this;
        refresh_list();
        setHasOptionsMenu(true);
        correo = Activity_Access.correo.getText().toString();
        return root;
    }


    public void refresh_list(){
        lista_Comentarios.clear();
        adapter = new Adaptador_Mis_Comentarios(lista_Comentarios, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_SELECT_COMENTARIOS_FROM_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println(response);
                progressDialog.dismiss();
                try{
                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Comentario comentario = new Comentario(
                                o.getString("id"),
                                o.getString("imagen"),
                                o.getString("usuario"),
                                o.getString("fecha"),
                                o.getString("comentario"),
                                o.getString("receta")
                        );
                        lista_Comentarios.add(comentario);
                    }
                    adapter = new Adaptador_Mis_Comentarios(lista_Comentarios, getActivity());
                    recyclerView.setAdapter(adapter);
                    if(lista_Comentarios.isEmpty()){
                        Toast.makeText(getActivity(), "No hay nada",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getActivity(), "Fallo al cargar comentarios",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("correo", correo);
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}