package com.example.re_z.Fragments.Access;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.re_z.R;


public class Fragment_Temporizador extends Fragment {

private TextView timerText;
private EditText defMinutos;
private Button set;
private ImageButton play;
private ImageButton plus10;

private CountDownTimer countDownTimer;
private long timeLeftMiliseconds = 600000; //10 mins
    private boolean timeRunning;


    public Fragment_Temporizador() {
        // Required empty public constructor
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        play =view.findViewById(R.id.TempButton);
        timerText=view.findViewById(R.id.Temporizador);
        defMinutos=view.findViewById(R.id.minutos);
        set=view.findViewById(R.id.set);
        plus10=view.findViewById(R.id.plus10);


        //Agregar 10 segundos
        plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            timeLeftMiliseconds=timeLeftMiliseconds+10000;
           if(timeRunning){
               stopTimer();
               timeRunning=false;
           }

            updateTimer();

            }
        });



        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=defMinutos.getText().toString();
              if (timeRunning){
                  stopTimer();
                  timeRunning=false;
              }

               if (input.isEmpty()){
                   Toast.makeText(getActivity(),"El campo se encuentra vacio",Toast.LENGTH_SHORT);

               }else{
                   if (input!="0" ){
                       long milisInput=Long.parseLong(defMinutos.getText().toString()) * 60000;
                       timeLeftMiliseconds=milisInput;
                       updateTimer();
                   }else{
                       Toast.makeText(getActivity(),"Ingrese un minuto valido",Toast.LENGTH_SHORT);
                       return;
                   }
               }


            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

    }



    private void startStop() {
        if (timeRunning){
            stopTimer();
        }

        else{
        startTimer();
        }


    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftMiliseconds,1000) {
            @Override
            public void onTick(long l) {
                    timeLeftMiliseconds = l;
                    updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        //Cambia la imagen a Stop
        play.setImageDrawable(getResources().getDrawable(R.drawable.stop));
        timeRunning=true;
    }



    private void updateTimer() {
        int minutos = (int) timeLeftMiliseconds/60000;
        int seconds = (int) timeLeftMiliseconds%60000/1000;

        String timeleft;
        timeleft= ""+minutos;
        timeleft+=":";
        if (seconds<10) timeleft+="0";
        timeleft+=seconds;
        timerText.setText(timeleft);

    }


    private void stopTimer() {
        play.setImageDrawable(getResources().getDrawable(R.drawable.start));
    countDownTimer.cancel();
    timeRunning=false;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_temporizador, container, false);
    }
}