package com.example.re_z.Fragments.Access;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.example.re_z.Clases.AdapterFavoritos;
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


public class Fragment_Favoritos extends Fragment {
    public static Fragment_Favoritos ma;
    protected Cursor cursor;
    ListView listview;
    List<Receta> lista_favoritos;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.activity_access_recetas_favoritos, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_favoritos, container, false);
        recyclerView = root.findViewById(R.id.lista_favoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());
        lista_favoritos = new ArrayList<>();
        ma = this;
        refresh_list();
        setHasOptionsMenu(true);
        return root;


    }


    public void refresh_list(){
        lista_favoritos.clear();
        //Cambio de adaptador
        adapter = new AdapterFavoritos(lista_favoritos, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_SELECT_Favoritos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    Toast.makeText(getActivity(), jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
                        lista_favoritos.add(receta);
                        //System.out.println("Imagen no." + i + o.getString("imagen"));
                    }
                    //Cambio de adaptador
                    adapter = new AdapterFavoritos(lista_favoritos, getActivity());
                    recyclerView.setAdapter(adapter);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getActivity(), "Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                //Obtenemos el correo del usuario
                Intent intent = ((Activity)getActivity()).getIntent();
                //Se lo mandamos al select_favoritos y busca solamente las recetas con el email registrado en favoritos
                params.put("email",intent.getStringExtra("email" ));
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }



}