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

    // MediaPlayer GLOBAL para que no se destruya nunca
    public static MediaPlayer mediaPlayer = null;

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

    ImageButton btn_play, btn_detener, btn_repetir, btn_anterior, btn_siguiente;
    ImageView imgPortada;
    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        // Recibir datos
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
        btnVolver = findViewById(R.id.btnVolverMenu);

        // Crear MediaPlayer solo UNA VEZ
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, canciones[posicion]);
        }
        imgPortada.setImageResource(portadas[posicion]);

        // PLAY
        btn_play.setOnClickListener(v -> mediaPlayer.start());

        // PAUSA
        btn_detener.setOnClickListener(v -> mediaPlayer.pause());

        // LOOP
        btn_repetir.setOnClickListener(v ->
                mediaPlayer.setLooping(!mediaPlayer.isLooping())
        );

        // SIGUIENTE
        btn_siguiente.setOnClickListener(v -> {
            posicion = (posicion + 1) % canciones.length;
            cambiarCancion();
        });

        // ANTERIOR
        btn_anterior.setOnClickListener(v -> {
            posicion = (posicion - 1 + canciones.length) % canciones.length;
            cambiarCancion();
        });

        // VOLVER SIN finish()
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(this, Menu.class);
            i.putExtra("email", email);
            i.putExtra("nombre", nombre);
            i.putExtra("puntos_totales", puntosTotales);
            i.putExtra("puntuacion", puntuacion);
            startActivity(i);
        });
    }

    private void cambiarCancion() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(this, canciones[posicion]);
        mediaPlayer.start();
        imgPortada.setImageResource(portadas[posicion]);
    }
}
