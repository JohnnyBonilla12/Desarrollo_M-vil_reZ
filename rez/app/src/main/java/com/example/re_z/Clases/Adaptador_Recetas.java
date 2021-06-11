package com.example.re_z.Clases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.re_z.R;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class Adaptador_Recetas extends RecyclerView.Adapter<Adaptador_Recetas.ViewHolder> {
    private List<Receta> lista_Recetas;
    private List<Receta> original_Recetas;
    private Context contexto;

    public Adaptador_Recetas(List<Receta> lista, Context contexto) {
        this.lista_Recetas = lista;
        this.contexto = contexto;
        this.original_Recetas = new ArrayList<>();
        this.original_Recetas.addAll(this.lista_Recetas);
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