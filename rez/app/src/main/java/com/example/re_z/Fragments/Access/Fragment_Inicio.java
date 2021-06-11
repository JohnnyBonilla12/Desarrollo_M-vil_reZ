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
import android.widget.SearchView;
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
import com.example.re_z.Activities.Recetas.Activity_Agregar;
import com.example.re_z.Clases.Adaptador_Mis_Recetas;
import com.example.re_z.Clases.Adaptador_Recetas;
import com.example.re_z.Clases.Configuration;
import com.example.re_z.Clases.Receta;
import com.example.re_z.Clases.RequestHandler;
import com.example.re_z.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Inicio extends Fragment implements SearchView.OnQueryTextListener{
    public static Fragment_Inicio ma;
    private List<Receta> lista_Recetas;
    private RecyclerView recyclerView;
    private Adaptador_Recetas adapter;
    private ProgressDialog progressDialog;
    private SearchView busqueda;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.access_fragment_mis_recetas, container, false);
        busqueda = root.findViewById(R.id.mis_recetas_busqueda);
        recyclerView = root.findViewById(R.id.mis_recetas_lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());
        lista_Recetas = new ArrayList<>();
        ma = this;
        busqueda.setOnQueryTextListener(this);
        refresh_list();
        return root;
    }

    public void refresh_list(){
        lista_Recetas.clear();
        adapter = new Adaptador_Recetas(lista_Recetas, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_SELECT, new Response.Listener<String>() {
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
                        Receta receta = new Receta(
                                o.getString("id"),
                                o.getString("imagen"),
                                o.getString("titulo"),
                                o.getString("dificultad"),
                                o.getString("porciones"),
                                o.getString("duracion"),
                                o.getString("ingredientes"),
                                o.getString("preparacion"),
                                o.getString("email")
                        );
                        lista_Recetas.add(receta);
                    }
                    adapter = new Adaptador_Recetas(lista_Recetas, getActivity());
                    recyclerView.setAdapter(adapter);
                    if(lista_Recetas.isEmpty()){
                        Toast.makeText(getActivity(), "No has subido ninguna RECETA",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getActivity(), "Fallo al cargar Recetas",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("correo", "correo");
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }
}