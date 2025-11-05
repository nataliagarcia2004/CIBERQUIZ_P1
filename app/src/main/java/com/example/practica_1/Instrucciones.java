package com.example.practica_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class Instrucciones extends AppCompatActivity {

    private int puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);
    }

    public void VolverAlMenu(View view) {
        Intent i = new Intent(this, Menu.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}