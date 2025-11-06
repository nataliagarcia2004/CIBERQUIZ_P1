package com.example.practica_1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta5 extends AppCompatActivity {
    private static final String TAG = "Pregunta5";
    private int puntuacion = 0;
    private int puntosTotales = 0;
    private String email;
    private String nombre;
    private MediaPlayer sonidoAcierto;
    private MediaPlayer sonidoError;
    private boolean respondido = false;

    private ListView listViewOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta5);

        // Obtener datos del Intent
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

        // Configurar el ListView
        listViewOpciones = findViewById(R.id.listViewOpciones);

        // Crear el array de opciones
        String[] opciones = {
                "Cambiar clave",
                "Usar PIN",
                "Verificación 2FA",
                "Antivirus"
        };

        // Crear el adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                opciones
        );

        // Asignar el adaptador al ListView
        listViewOpciones.setAdapter(adapter);

        // Configurar el listener para cuando se seleccione una opción
        listViewOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Solo responder si no se ha respondido antes
                if (!respondido) {
                    String respuestaSeleccionada = parent.getItemAtPosition(position).toString();
                    responderPregunta(respuestaSeleccionada);
                }
            }
        });
    }

    private void responderPregunta(String respuesta) {
        // Marcar como respondido
        respondido = true;

        Log.d(TAG, "Respuesta seleccionada: '" + respuesta + "'");

        String respuestaCorrecta = "Verificación 2FA";

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

            // Esperar 1.5 segundos antes de pasar a la pantalla de puntos
            listViewOpciones.postDelayed(() -> {
                Intent i = new Intent(this, Puntos.class);
                i.putExtra("puntuacion", puntuacion);
                i.putExtra("puntos_totales", puntosTotales);
                i.putExtra("email", email);
                i.putExtra("nombre", nombre);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
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
            listViewOpciones.postDelayed(() -> {
                respondido = false;
            }, 2000);
        }
    }

    public void Pregunta5_Puntos(View view) {
        Intent i = new Intent(this, Puntos.class);
        i.putExtra("puntuacion", puntuacion);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
        finish();
    }

    public void Pregunta5_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        i.putExtra("puntuacion", puntuacion);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
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