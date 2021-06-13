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
import com.example.re_z.Activities.Comentarios.Activity_Editar;
import com.example.re_z.Fragments.Access.Fragment_Mis_Comentarios;
import com.example.re_z.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Adaptador_Mis_Comentarios extends RecyclerView.Adapter<Adaptador_Mis_Comentarios.ViewHolder>{
    private List<Comentario> lista_Comentarios;
    private Context contexto;

    public Adaptador_Mis_Comentarios(List<Comentario> lista, Context contexto) {
        this.lista_Comentarios = lista;
        this.contexto = contexto;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.access_cardview_comentario, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Comentario comentario = lista_Comentarios.get(position);

        byte[] decodedString = Base64.decode(comentario.getImagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.imagen.setImageBitmap(decodedByte);
        holder.usuario.setText("Usuario: " + comentario.getUsuario());
        holder.fecha.setText("Fecha: " + comentario.getFecha());
        holder.comentario.setText("Comentario: " + comentario.getComentario());

        holder.card_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogitem = {"Editar", "Borrar"};
                builder.setTitle(comentario.getUsuario());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent editar = new Intent(view.getContext(), Activity_Editar.class);
                                byte[] imagen_editar = Base64.decode(comentario.getImagen(), Base64.DEFAULT);
                                Bitmap bitmap_editar = BitmapFactory.decodeByteArray(imagen_editar, 0, imagen_editar.length);
                                String ruta_editar = saveToInternalStorage(bitmap_editar);

                                editar.putExtra("id", comentario.getId());
                                editar.putExtra("imagen", ruta_editar);
                                editar.putExtra("usuario", comentario.getUsuario());
                                editar.putExtra("fecha", comentario.getFecha());
                                editar.putExtra("comentario", comentario.getComentario());
                                editar.putExtra("receta", comentario.getReceta());
                                view.getContext().startActivity(editar);
                                break;
                            case 1 :
                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(comentario.getUsuario() + ". Fecha: " + comentario.getFecha());
                                builderDel.setMessage("¿Seguro que deseas borrar este comentario?");
                                builderDel.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_DELETE_COMENTARIOS, new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    System.out.println(jsonObject.getString("imagen"));
                                                } catch (JSONException e){
                                                    e.printStackTrace();
                                                }
                                                dialog.hide();
                                                dialog.dismiss();
                                                Toast.makeText(view.getContext(),"Comentario Borrado: "+ comentario.getFecha(),Toast.LENGTH_LONG).show();
                                                Fragment_Mis_Comentarios.ma.refresh_list();
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
                                                params.put("id", comentario.getId());
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
        return lista_Comentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView usuario;
        public TextView fecha;
        public TextView comentario;
        public CardView card_comentario;

        public ViewHolder(View itemView ) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.card_view_comentario_imagen);
            usuario = (TextView) itemView.findViewById(R.id.card_view_comentario_usuario);
            fecha = (TextView) itemView.findViewById(R.id.card_view_comentario_fecha);
            comentario = (TextView) itemView.findViewById(R.id.card_view_comentario_comentario);
            card_comentario = (CardView) itemView.findViewById(R.id.card_view_comentario);
        }
    }
}
