package com.example.re_z.Clases;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.re_z.Activities.Activity_Access;
import com.example.re_z.Activities.Activity_Main;
import com.example.re_z.Activities.Recetas.Activity_Detalles;
import com.example.re_z.Fragments.Access.Fragment_Favoritos;
import com.example.re_z.Fragments.Main.Fragment_Login;
import com.example.re_z.R;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterFavoritos extends RecyclerView.Adapter<AdapterFavoritos.ViewHolder> {

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

    private List<Receta> lista_Recetas;
    private Context contexto;
    private ProgressDialog dialog;


    public AdapterFavoritos(List<Receta> lista, Context contexto) {
        this.lista_Recetas = lista;
        this.contexto = contexto;
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
                Intent intent;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogitem = {"View Data","Delete Data"};
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
                                view.getContext().startActivity(detalles);
                                break;
                            case 1 :
                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(receta.getTitulo());
                                builderDel.setMessage("Are You Sure, You Want to Delete Data?");
                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_DELETE_Favoritos, new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                dialog.hide();
                                                dialog.dismiss();
                                                Toast.makeText(view.getContext(),"Receta Borrada Exitosamente "+ receta.getTitulo(),Toast.LENGTH_LONG).show();
                                                Fragment_Favoritos.ma.refresh_list();
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

                                                //Conseguir correo de usuario desde adapter
                                                Intent intent = ((Activity)view.getContext()).getIntent();
                                               //Envio de correo
                                                params.put("email",intent.getStringExtra("email") );
                                                return (HashMap<String, String>) params;

                                            }
                                        };
                                        RequestHandler.getInstance(view.getContext()).addToRequestQueue(stringRequest);
                                        dialogInterface.dismiss();
                                    }
                                });

                                builderDel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
