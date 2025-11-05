package com.example.practica_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class Menu extends AppCompatActivity {

    private int puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", puntuacion);
    }
    public void Menu_Pregunta1(View view) {
        Intent i = new Intent (this, Pregunta1.class);
        i.putExtra("puntuacion", 0);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void SalirApp(View view) {
        Intent i = new Intent (this, MainActivity.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void Puntos(View view) {
        Intent i = new Intent (this, Puntos.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void AbrirInstrucciones(View view) {
        Intent i = new Intent(this, Instrucciones.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}