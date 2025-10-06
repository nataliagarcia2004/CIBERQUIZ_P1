package com.example.practica_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;


public class Puntos extends AppCompatActivity {

    private int puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", puntuacion);//tocado

        TextView puntuacionTextView = findViewById(R.id.puntuacionTextView);
        puntuacionTextView.setText("PUNTUACIÓN FINAL: " + puntuacion);
    }

    public void Puntos_Pregunta1(View view) {
        Intent i = new Intent(this, Pregunta1.class);
        i.putExtra("puntuacion", 0);
        startActivity(i);
    }

    public void Puntos_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
    }
}
