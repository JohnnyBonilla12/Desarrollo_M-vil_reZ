package com.example.re_z.Fragments.Access;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.re_z.Activities.Activity_Main;
import com.example.re_z.R;

public class Fragment_Perfil extends Fragment {
    private TextView usuario, recetas, comentarios, favoritos;
    private String Id, receta, fecha, comentario, Email;
    private Button buttonConfirmar;
    private Boolean valid = true;
    ProgressDialog progressDialog;

    public Fragment_Perfil(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //usuario = (TextView) findViewById(R.id.textoReceta);

    }

    /*
    public void eliminarPerfil(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Activity_Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            }
        });
    }
    */



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.access_fragment_perfil, container, false);
    }
}
