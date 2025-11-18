package com.example.practica_1;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ReproductorActivity extends AppCompatActivity {

    private int puntuacion;
    private int puntosTotales;
    private String email;
    private String nombre;

    int[] canciones = {
            R.raw.breeze,
            R.raw.disco,
            R.raw.migente,
            R.raw.miss,
            R.raw.natural,
            R.raw.newyork,
            R.raw.rules,
            R.raw.x
    };

    int[] portadas = {
            R.drawable.portada1,
            R.drawable.portada2,
            R.drawable.portada3,
            R.drawable.portada4,
            R.drawable.portada5,
            R.drawable.portada6,
            R.drawable.portada1,
            R.drawable.portada2
    };

    int posicion = 0;
    MediaPlayer mediaPlayer;

    ImageButton btn_play, btn_detener, btn_repetir, btn_anterior, btn_siguiente;
    ImageView imgPortada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);
        puntosTotales = intent.getIntExtra("puntos_totales", 0);
        email = intent.getStringExtra("email");
        nombre = intent.getStringExtra("nombre");

        imgPortada = findViewById(R.id.imgPortada);

        btn_play = findViewById(R.id.btn_play);
        btn_detener = findViewById(R.id.btn_detener);
        btn_repetir = findViewById(R.id.btn_repetir);
        btn_anterior = findViewById(R.id.btn_anterior);
        btn_siguiente = findViewById(R.id.btn_siguiente);

        mediaPlayer = MediaPlayer.create(this, canciones[posicion]);
        imgPortada.setImageResource(portadas[posicion]);

        btn_play.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });

        btn_detener.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(this, canciones[posicion]);
            }
        });

        btn_repetir.setOnClickListener(v -> {
            mediaPlayer.setLooping(!mediaPlayer.isLooping());
        });

        btn_siguiente.setOnClickListener(v -> {
            if (posicion < canciones.length - 1)
                posicion++;
            else
                posicion = 0;
            cambiarCancion();
        });

        btn_anterior.setOnClickListener(v -> {
            if (posicion > 0)
                posicion--;
            else
                posicion = canciones.length - 1;
            cambiarCancion();
        });
        Button btnVolver = findViewById(R.id.btnVolverMenu);

        btnVolver.setOnClickListener(v -> {
            finish(); // vuelve al menú
        });


    }

    private void cambiarCancion() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();

        mediaPlayer = MediaPlayer.create(this, canciones[posicion]);
        mediaPlayer.start();
        imgPortada.setImageResource(portadas[posicion]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
