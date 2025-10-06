package com.example.practica_1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta3 extends AppCompatActivity {
    private int puntuacion = 0;
    private MediaPlayer sonidoAcierto;
    private MediaPlayer sonidoError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta3);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);

        // Inicializar los MediaPlayer con los sonidos
        sonidoAcierto = MediaPlayer.create(this, R.raw.aplausos);
        sonidoError = MediaPlayer.create(this, R.raw.abucheo);
    }

    public void onOptionSelected(View view) {
        // Verifica cuál opción fue seleccionada
        if (view.getId() == R.id.option1Image) {
            // Opción 1
            puntuacion -= 2;

            // Reproducir sonido de error
            if (sonidoError != null) {
                sonidoError.start();
            }

            Toast.makeText(this, "Error: Matrícula gimansio es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.option2Image) {
            // Opción 2
            puntuacion -= 2;

            // Reproducir sonido de error
            if (sonidoError != null) {
                sonidoError.start();
            }

            Toast.makeText(this, "Error: LOWI es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.option3Image) {
            // Opción 3 (Correcta)
            puntuacion += 3;

            // Reproducir sonido de acierto
            if (sonidoAcierto != null) {
                sonidoAcierto.start();
            }

            Toast.makeText(this, "¡ACIERTO! Has ganado 3 puntos.", Toast.LENGTH_SHORT).show();
            Pregunta3_Pregunta4(view);
        } else if (view.getId() == R.id.option4Image) {
            // Opción 4
            puntuacion -= 2;

            // Reproducir sonido de error
            if (sonidoError != null) {
                sonidoError.start();
            }

            Toast.makeText(this, "Error: Seguridad social es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void Pregunta3_Pregunta4(View view) {
        Intent i = new Intent(this, Pregunta4.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void Pregunta3_Menu(View view) {
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