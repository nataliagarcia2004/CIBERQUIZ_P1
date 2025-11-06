package com.example.practica_1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta4 extends AppCompatActivity {
    private static final String TAG = "Pregunta4";
    private int puntuacion = 0;
    private MediaPlayer sonidoAcierto;
    private MediaPlayer sonidoError;
    private boolean respondido = false;
    private int puntosTotales;
    private String email;
    private String nombre;

    private Spinner spinnerOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta4);

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

        // Configurar el Spinner
        spinnerOpciones = findViewById(R.id.spinnerOpciones);

        // Crear el array de opciones
        String[] opciones = {"Selecciona una opción", "HTTP", "HTTPS", "FTP", "SMTP"};

        // Crear el adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                opciones
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        spinnerOpciones.setAdapter(adapter);

        // Configurar el listener para cuando se seleccione una opción
        spinnerOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Ignorar la primera opción (placeholder) y si ya se ha respondido
                if (position > 0 && !respondido) {
                    String respuestaSeleccionada = parent.getItemAtPosition(position).toString();
                    responderPregunta(respuestaSeleccionada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    private void responderPregunta(String respuesta) {
        // Marcar como respondido
        respondido = true;

        Log.d(TAG, "Respuesta seleccionada: '" + respuesta + "'");

        String respuestaCorrecta = "HTTPS";

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

            Toast.makeText(this, "¡ACIERTO! Has ganado 3 puntos.", Toast.LENGTH_SHORT).show();

            // Esperar 1.5 segundos antes de pasar a la siguiente pregunta
            spinnerOpciones.postDelayed(() -> {
                Intent i = new Intent(this, Pregunta5.class);
                i.putExtra("puntuacion", puntuacion);
                i.putExtra("puntos_totales", puntosTotales);
                i.putExtra("email", email);
                i.putExtra("nombre", nombre);

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
            }

            Toast.makeText(this, "Error: " + respuesta + " es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_LONG).show();

            // Permitir responder de nuevo después de 2 segundos
            spinnerOpciones.postDelayed(() -> {
                respondido = false;
                spinnerOpciones.setSelection(0); // Volver a la opción inicial
            }, 2000);
        }
    }

    public void Pregunta4_Pregunta5(View view) {
        Intent i = new Intent(this, Pregunta5.class);
        i.putExtra("puntuacion", puntuacion);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    public void Pregunta4_Menu(View view) {
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