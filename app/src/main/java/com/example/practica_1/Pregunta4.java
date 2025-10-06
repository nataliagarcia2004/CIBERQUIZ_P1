package com.example.practica_1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta4 extends AppCompatActivity {
    private int puntuacion = 0;
    private MediaPlayer sonidoAcierto;
    private MediaPlayer sonidoError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta4);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);

        // Inicializar los MediaPlayer con los sonidos
        sonidoAcierto = MediaPlayer.create(this, R.raw.aplausos);
        sonidoError = MediaPlayer.create(this, R.raw.abucheo);
    }

    public void responderPregunta(View view) {
        Button respuestaButton = (Button) view;
        String respuesta = respuestaButton.getText().toString();

        String respuestaCorrecta = "HTTPS";

        if (respuesta.equals(respuestaCorrecta)) {
            puntuacion += 3;

            // Reproducir sonido de acierto
            if (sonidoAcierto != null) {
                sonidoAcierto.start();
            }

            Toast.makeText(this, "¡ACIERTO! Has ganado 3 puntos.", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, Pregunta5.class);
            i.putExtra("puntuacion", puntuacion);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else {
            puntuacion -= 2;

            // Reproducir sonido de error
            if (sonidoError != null) {
                sonidoError.start();
            }

            Toast.makeText(this, "Error: " + respuesta + " es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void Pregunta4_Pregunta5(View view) {
        Intent i = new Intent(this, Pregunta5.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void Pregunta4_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos de los MediaPlayer
        if (sonidoAcierto != null) {
            sonidoAcierto.release();
            sonidoAcierto = null;
        }
        if (sonidoError != null) {
            sonidoError.release();
            sonidoError = null;
        }
    }
}