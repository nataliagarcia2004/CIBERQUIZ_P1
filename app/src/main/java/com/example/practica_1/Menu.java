package com.example.practica_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Menu extends AppCompatActivity {

    private int puntuacion;
    private int puntosTotales;
    private String email;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Configurar el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setTitle("CiberQuiz");
        }

        // Obtener datos del Intent
        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);
        puntosTotales = intent.getIntExtra("puntos_totales", 0);
        email = intent.getStringExtra("email");
        nombre = intent.getStringExtra("nombre");
    }

    public void Menu_Pregunta1(View view) {
        Intent i = new Intent(this, Pregunta1.class);
        i.putExtra("puntuacion", 0); // Nueva ronda empieza en 0
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void SalirApp(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    public void Puntos(View view) {
        Intent i = new Intent(this, Puntos.class);
        i.putExtra("puntuacion", puntuacion);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void AbrirInstrucciones(View view) {
        Intent i = new Intent(this, Instrucciones.class);
        i.putExtra("puntuacion", puntuacion);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}