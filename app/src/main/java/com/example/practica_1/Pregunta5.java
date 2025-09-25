package com.example.practica_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta5 extends AppCompatActivity {
    private int puntuacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta5);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);
    }


    public void responderPregunta(View view) {
        Button respuestaButton = (Button) view;
        String respuesta = respuestaButton.getText().toString();

        String respuestaCorrecta = "Tomate";


        if (respuesta.equals(respuestaCorrecta)) {
            puntuacion += 3;
            Toast.makeText(this, "¡Correcto! Has ganado 3 puntos.", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, Puntos.class);
            i.putExtra("puntuacion", puntuacion);
            startActivity(i);

        } else {
            puntuacion -= 2;
            Toast.makeText(this, "Error: " + respuesta + " es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        }
    }


    public void Pregunta5_Puntos(View view) {
        Intent i = new Intent(this, Puntos.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
    }


    public void Pregunta5_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
}
