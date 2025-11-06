package com.example.practica_1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta2 extends AppCompatActivity {
    private static final String TAG = "Pregunta2";
    private int puntuacion = 0;
    private MediaPlayer sonidoAcierto;
    private MediaPlayer sonidoError;
    private boolean respondido = false;
    private int puntosTotales;
    private String email;
    private String nombre;
    private RadioGroup radioGroupOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta2);

        // Recibe la puntuación desde la pregunta 1
        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);
        puntosTotales = intent.getIntExtra("puntos_totales", 0);
        email = intent.getStringExtra("email");
        nombre = intent.getStringExtra("nombre");


        // Inicializar los MediaPlayer con los sonidos
        try {
            sonidoAcierto = MediaPlayer.create(this, R.raw.aplausos);
            sonidoError = MediaPlayer.create(this, R.raw.abucheo);

            if (sonidoAcierto == null) {
                Log.e(TAG, "No se pudo cargar el sonido de acierto");
            }
            if (sonidoError == null) {
                Log.e(TAG, "No se pudo cargar el sonido de error");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar los sonidos", e);
        }

        // Obtener el RadioGroup
        radioGroupOpciones = findViewById(R.id.radioGroupOpciones);

        // Configurar el listener para cuando se seleccione una opción
        radioGroupOpciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Solo responder si no se ha respondido antes
                if (!respondido) {
                    responderPregunta(checkedId);
                }
            }
        });
    }

    private void responderPregunta(int checkedId) {
        // Marcar como respondido
        respondido = true;

        // Obtener el RadioButton seleccionado
        RadioButton radioButtonSeleccionado = findViewById(checkedId);
        String respuesta = radioButtonSeleccionado.getText().toString().trim();

        Log.d(TAG, "Respuesta seleccionada: '" + respuesta + "'");

        // Respuesta correcta
        String respuestaCorrecta = "Correo falso";

        if (respuesta.equals(respuestaCorrecta)) {
            puntuacion += 3;

            // Reproducir sonido de acierto
            if (sonidoAcierto != null) {
                try {
                    sonidoAcierto.start();
                } catch (Exception e) {
                    Log.e(TAG, "Error al reproducir sonido de acierto", e);
                }
            }

            Toast.makeText(this, "¡Correcto! Has ganado 3 puntos.", Toast.LENGTH_SHORT).show();

            // Esperar 1.5 segundos antes de pasar a la siguiente pregunta
            radioGroupOpciones.postDelayed(() -> {
                Intent i = new Intent(this, Pregunta3.class);
                i.putExtra("puntuacion", puntuacion);
                i.putExtra("puntos_totales", puntosTotales);
                i.putExtra("email", email);
                i.putExtra("nombre", nombre);
                startActivity(i);

                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }, 1500);

        } else {
            puntuacion -= 2;

            Log.d(TAG, "Respuesta incorrecta. Puntuación actual: " + puntuacion);

            // Reproducir sonido de error
            if (sonidoError != null) {
                try {
                    sonidoError.start();
                    Log.d(TAG, "Sonido de error reproducido");
                } catch (Exception e) {
                    Log.e(TAG, "Error al reproducir sonido de error", e);
                }
            } else {
                Log.e(TAG, "sonidoError es null");
            }

            Toast.makeText(this, "Error: " + respuesta + " es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_LONG).show();

            // Permitir responder de nuevo después de 2 segundos
            radioGroupOpciones.postDelayed(() -> {
                respondido = false;
                radioGroupOpciones.clearCheck(); // Limpiar la selección
            }, 2000);
        }
    }

    public void Pregunta2_Pregunta3(View view) {
        Intent i = new Intent(this, Pregunta3.class);
        i.putExtra("puntuacion", puntuacion);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    public void Pregunta2_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        i.putExtra("puntuacion", puntuacion);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
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