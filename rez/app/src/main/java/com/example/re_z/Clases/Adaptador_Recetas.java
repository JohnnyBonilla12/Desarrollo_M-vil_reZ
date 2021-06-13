package com.example.re_z.Clases;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.re_z.Activities.Activity_Access;
import com.example.re_z.Activities.Comentarios.Activity_Agregar;
import com.example.re_z.Activities.Comentarios.Activity_Editar;
import com.example.re_z.Fragments.Fragment_Comentarios;
import com.example.re_z.Fragments.Main.Fragment_Registro;
import com.example.re_z.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Adaptador_Recetas extends RecyclerView.Adapter<Adaptador_Recetas.ViewHolder> {
    private List<Receta> lista_Recetas;
    private List<Receta> original_Recetas;
    private Context contexto;
    private Boolean validacion;


    public interface VolleyCallback {
        void setValid(boolean result);

    }


    public Adaptador_Recetas(List<Receta> lista, Context contexto) {
        this.lista_Recetas = lista;
        this.contexto = contexto;
        this.original_Recetas = new ArrayList<>();
        this.original_Recetas.addAll(this.lista_Recetas);
        this.validacion = false;

    }

    public void filter(final String search) {
        if (search.length() == 0) {
            lista_Recetas.clear();
            lista_Recetas.addAll(original_Recetas);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                lista_Recetas.clear();
                List<Receta> collect = original_Recetas.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(search))
                        .collect(Collectors.toList());

                lista_Recetas.addAll(collect);
            } else {
                lista_Recetas.clear();
                for (Receta i : original_Recetas) {
                    if (i.getTitulo().toLowerCase().contains(search)) {
                        lista_Recetas.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Adaptador_Recetas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.access_cardview_receta, parent, false);
        return new Adaptador_Recetas.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(Adaptador_Recetas.ViewHolder holder, final int position) {
        final Receta receta = lista_Recetas.get(position);

        byte[] decodedString = Base64.decode(receta.getImagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.imagen.setImageBitmap(decodedByte);
        holder.titulo.setText("Nombre: " + receta.getTitulo());
        holder.dificultad.setText("Dificultad: " + receta.getDificultad());
        holder.duracion.setText("Duracion: " + receta.getDuracion());

        //Mi modificación Empieza aqui
        //Agregar receta a favoritos
        holder.card_receta.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                dialog.setMessage("Cargando");
                final CharSequence[] dialogitem = {"Agregar a favoritos", "Ver Comentarios", "Agregar Comentario"};
                builder.setTitle(receta.getTitulo());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {

                            case 0:
                                //Agregar Favoritos
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_ADD_Favoritos, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject.getString("success").equals("false")) {
                                                Toast.makeText(v.getContext(), "Ya se encuentra esta receta en favoritos", Toast.LENGTH_LONG).show();
                                            }
                                            if (jsonObject.getString("success").equals("true")) {
                                                Toast.makeText(v.getContext(), "Receta Agregada Exitosamente", Toast.LENGTH_LONG).show();
                                            }

                                            if (jsonObject.getString("success").equals("error")) {
                                                Toast.makeText(v.getContext(), "No se pudo agregar la receta", Toast.LENGTH_LONG).show();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(v.getContext(), "Hubo un problema al agregar la receta a favoritos", Toast.LENGTH_LONG).show();
                                    }
                                }) {
                                    protected HashMap<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        String id_receta = receta.getId();
                                        //ID de la receta
                                        params.put("id", id_receta);

                                        //Conseguir correo de usuario desde adapter
                                        Intent intent = ((Activity) v.getContext()).getIntent();
                                        //Envio de correo
                                        params.put("email", intent.getStringExtra("email"));

                                        return (HashMap<String, String>) params;

                                    }
                                }; //Fin de string Request
                                stringRequest.setShouldCache(false);
                                RequestHandler.getInstance(v.getContext()).addToRequestQueue(stringRequest);

                                break;

                            case 1:
                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                Fragment myFragment = new Fragment_Comentarios();
                                Bundle args = new Bundle();
                                args.putString("receta", receta.getId());
                                myFragment.setArguments(args);
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.access_nav, myFragment).addToBackStack(null).commit();
                                break;

                            case 2:
                                Intent add = new Intent(v.getContext(), Activity_Agregar.class);

                                String correo = Activity_Access.correo.getText().toString();
                                add.putExtra("id", receta.getId());
                                add.putExtra("correo", correo);
                                v.getContext().startActivity(add);
                                break;
                        }
                        //Fin de onclick Dialog


                    }
                });
                builder.create().show();


            }


        });

        //Mi modificación  termina hasta aqui


    }

    @Override
    public int getItemCount() {
        return lista_Recetas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView titulo;
        public TextView dificultad;
        public TextView duracion;
        public CardView card_receta;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.card_view_receta_imagen);
            titulo = (TextView) itemView.findViewById(R.id.card_view_receta_titulo);
            dificultad = (TextView) itemView.findViewById(R.id.card_view_receta_dificultad);
            duracion = (TextView) itemView.findViewById(R.id.card_view_receta_duracion);
            card_receta = (CardView) itemView.findViewById(R.id.card_view_receta);
        }
    }


}