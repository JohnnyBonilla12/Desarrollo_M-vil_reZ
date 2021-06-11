package com.example.re_z.Clases;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.re_z.Activities.Recetas.Activity_Detalles;
import com.example.re_z.Activities.Recetas.Activity_Editar;
import com.example.re_z.Fragments.Access.Fragment_Mis_Recetas;
import com.example.re_z.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Adaptador_Mis_Recetas extends RecyclerView.Adapter<Adaptador_Mis_Recetas.ViewHolder>{
    private List<Receta> lista_Recetas;
    private List<Receta> original_Recetas;
    private Context contexto;

    public Adaptador_Mis_Recetas(List<Receta> lista, Context contexto) {
        this.lista_Recetas = lista;
        this.contexto = contexto;
        this.original_Recetas =  new ArrayList<>();
        this.original_Recetas.addAll(this.lista_Recetas);
    }

    public void filter(final String search) {
        if (search.length() == 0) {
            lista_Recetas.clear();
            lista_Recetas.addAll(original_Recetas);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                lista_Recetas.clear();
                List<Receta> collect = original_Recetas.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(search))
                        .collect(Collectors.toList());

                lista_Recetas.addAll(collect);
            }
            else {
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
    
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(contexto);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"imagen.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.access_cardview_receta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Receta receta = lista_Recetas.get(position);

        byte[] decodedString = Base64.decode(receta.getImagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.imagen.setImageBitmap(decodedByte);
        holder.titulo.setText("Nombre: " + receta.getTitulo());
        holder.dificultad.setText("Dificultad: " + receta.getDificultad());
        holder.duracion.setText("Duracion: " + receta.getDuracion());

        holder.card_receta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogitem = {"Detalles", "Editar", "Borrar"};
                builder.setTitle(receta.getTitulo());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent detalles = new Intent(view.getContext(), Activity_Detalles.class);
                                byte[] imagen_detalles = Base64.decode(receta.getImagen(), Base64.DEFAULT);
                                Bitmap bitmap_detalles = BitmapFactory.decodeByteArray(imagen_detalles, 0, imagen_detalles.length);
                                String ruta_detalles = saveToInternalStorage(bitmap_detalles);

                                detalles.putExtra("id", receta.getId());
                                detalles.putExtra("imagen", ruta_detalles);
                                detalles.putExtra("titulo", receta.getTitulo());
                                detalles.putExtra("dificultad", receta.getDificultad());
                                detalles.putExtra("porciones", receta.getPorciones());
                                detalles.putExtra("duracion", receta.getDuracion());
                                detalles.putExtra("ingredientes", receta.getIngredientes());
                                detalles.putExtra("preparacion", receta.getPreparacion());
                                detalles.putExtra("correo", receta.getEmail());
                                view.getContext().startActivity(detalles);
                                break;
                            case 1 :
                                Intent editar = new Intent(view.getContext(), Activity_Editar.class);
                                byte[] imagen_editar = Base64.decode(receta.getImagen(), Base64.DEFAULT);
                                Bitmap bitmap_editar = BitmapFactory.decodeByteArray(imagen_editar, 0, imagen_editar.length);
                                String ruta_editar = saveToInternalStorage(bitmap_editar);

                                editar.putExtra("id", receta.getId());
                                editar.putExtra("imagen", ruta_editar);
                                editar.putExtra("titulo", receta.getTitulo());
                                editar.putExtra("dificultad", receta.getDificultad());
                                editar.putExtra("porciones", receta.getPorciones());
                                editar.putExtra("duracion", receta.getDuracion());
                                editar.putExtra("ingredientes", receta.getIngredientes());
                                editar.putExtra("preparacion", receta.getPreparacion());
                                editar.putExtra("correo", receta.getEmail());
                                view.getContext().startActivity(editar);
                                break;
                            case 2 :
                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(receta.getTitulo());
                                builderDel.setMessage("¿Seguro que deseas borrar esta receta?");
                                builderDel.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_DELETE, new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            //System.out.println(response);
                                            dialog.hide();
                                            dialog.dismiss();
                                            Toast.makeText(view.getContext(),"Receta Borrada: "+ receta.getTitulo(),Toast.LENGTH_LONG).show();
                                            Fragment_Mis_Recetas.ma.refresh_list();
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            dialog.hide();
                                            dialog.dismiss();
                                        }
                                    }){
                                        protected HashMap<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params= new HashMap<>();
                                            params.put("id", receta.getId());
                                            return (HashMap<String, String>) params;
                                        }
                                    };
                                    RequestHandler.getInstance(view.getContext()).addToRequestQueue(stringRequest);
                                    dialogInterface.dismiss();
                                }
                            });

                            builderDel.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                        builderDel.create().show();
                        break;
                        }
                    }
                });
                builder.create().show();
            }
        });
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

        public ViewHolder(View itemView ) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.card_view_receta_imagen);
            titulo = (TextView) itemView.findViewById(R.id.card_view_receta_titulo);
            dificultad = (TextView) itemView.findViewById(R.id.card_view_receta_dificultad);
            duracion = (TextView) itemView.findViewById(R.id.card_view_receta_duracion);
            card_receta = (CardView) itemView.findViewById(R.id.card_view_receta);
        }
    }
}