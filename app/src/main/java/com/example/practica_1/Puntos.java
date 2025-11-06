package com.example.practica_1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class Puntos extends AppCompatActivity {

    private AdminSQLiteOpenHelper admin;
    private int puntuacionRonda;
    private int puntosTotales;
    private String email;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        // Inicializar el administrador de base de datos
        admin = new AdminSQLiteOpenHelper(this);

        // Obtener datos del Intent
        Intent intent = getIntent();
        puntuacionRonda = intent.getIntExtra("puntuacion", 0);
        puntosTotales = intent.getIntExtra("puntos_totales", 0);
        email = intent.getStringExtra("email");
        nombre = intent.getStringExtra("nombre");

        // Calcular nuevos puntos totales
        int nuevosPuntosTotales = puntosTotales + puntuacionRonda;

        // Actualizar puntos en la base de datos
        actualizarPuntosEnBD(email, nuevosPuntosTotales);

        // Actualizar variable local
        puntosTotales = nuevosPuntosTotales;

        // Mostrar puntuaciones en pantalla
        TextView puntuacionRondaTextView = findViewById(R.id.puntuacionRondaTextView);
        TextView puntosTotalesTextView = findViewById(R.id.puntosTotalesTextView);

        puntuacionRondaTextView.setText("Puntos esta ronda: " + puntuacionRonda);
        puntosTotalesTextView.setText("Total acumulado: " + puntosTotales + " puntos");
    }

    private void actualizarPuntosEnBD(String userEmail, int nuevosPuntos) {
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(AdminSQLiteOpenHelper.COLUMN_PUNTOS_TOTALES, nuevosPuntos);

        db.update(
                AdminSQLiteOpenHelper.TABLE_USUARIOS,
                valores,
                AdminSQLiteOpenHelper.COLUMN_EMAIL + "=?",
                new String[]{userEmail}
        );

        db.close();
    }

    public void Puntos_Pregunta1(View view) {
        Intent i = new Intent(this, Pregunta1.class);
        i.putExtra("puntuacion", 0);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        i.putExtra("puntos_totales", puntosTotales);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
    }

    public void Puntos_Menu(View view) {
        Intent i = new Intent(this, Menu.class);
        i.putExtra("email", email);
        i.putExtra("nombre", nombre);
        i.putExtra("puntos_totales", puntosTotales);
        i.putExtra("puntuacion", 0);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
    }
}