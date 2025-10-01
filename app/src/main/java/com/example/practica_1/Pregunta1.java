package com.example.practica_1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta1 extends AppCompatActivity {
    private int puntuacion = 0;
    private MediaPlayer sonidoAcierto;
    private MediaPlayer sonidoError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta1);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);

        // Inicializar los MediaPlayer con los sonidos
        sonidoAcierto = MediaPlayer.create(this, R.raw.aplausos);
        sonidoError = MediaPlayer.create(this, R.raw.abucheo);
    }

    public void responderPregunta(View view) {
        Button respuestaButton = (Button) view;
        String respuesta = respuestaButton.getText().toString();
        String respuestaCorrecta = "M1c0ntr4s3ñ4!";

        if (respuesta.equals(respuestaCorrecta)) {
            puntuacion += 3;

            // Reproducir sonido de acierto
            if (sonidoAcierto != null) {
                sonidoAcierto.start();
            }

            Toast.makeText(this, "¡ACIERTO! Has ganado 3 puntos.", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, Pregunta2.class);
            i.putExtra("puntuacion", puntuacion);
            startActivity(i);

        } else {
            puntuacion -= 2;

            // Reproducir sonido de error
            if (sonidoError != null) {
                sonidoError.start();
            }

            Toast.makeText(this, "Error: " + respuesta + " es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void Pregunta1_Pregunta2(View view) {
        Intent i = new Intent(this, Pregunta2.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
    }

    public void Pregunta1_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
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