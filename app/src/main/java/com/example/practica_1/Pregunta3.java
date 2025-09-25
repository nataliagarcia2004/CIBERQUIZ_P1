package com.example.practica_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pregunta3 extends AppCompatActivity {
    private int puntuacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta3);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);
    }


    public void onOptionSelected(View view) {
        // Verifica cuál opción fue seleccionada
        if (view.getId() == R.id.option1Image) {
            // Opción 1
            puntuacion -= 2;
            Toast.makeText(this, "Error: Matrícula gimansio es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.option2Image) {
            // Opción 2
            puntuacion -= 2;
            Toast.makeText(this, "Error: LOWI es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.option3Image) {
            // Opción 3 (Correcta)
            puntuacion += 3;
            Toast.makeText(this, "¡Correcto! Has ganado 3 puntos.", Toast.LENGTH_SHORT).show();
            Pregunta3_Pregunta4(view);
        } else if (view.getId() == R.id.option4Image) {
            // Opción 4
            puntuacion -= 2;
            Toast.makeText(this, "Error: Seguridad social es incorrecto. Has perdido 2 puntos.", Toast.LENGTH_SHORT).show();
        }
    }



    public void Pregunta3_Pregunta4(View view) {
        Intent i = new Intent(this, Pregunta4.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
    }


    public void Pregunta3_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
}
