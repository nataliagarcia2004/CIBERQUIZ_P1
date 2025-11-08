package com.example.practica_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class Ranking extends AppCompatActivity {

    private LinearLayout rankingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        // Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rankingContainer = findViewById(R.id.rankingContainer);

        cargarRanking();
    }

    private void cargarRanking() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        // Consulta para obtener usuarios ordenados por puntos
        Cursor cursor = db.query(
                AdminSQLiteOpenHelper.TABLE_USUARIOS,
                new String[]{
                        AdminSQLiteOpenHelper.COLUMN_NOMBRE,
                        AdminSQLiteOpenHelper.COLUMN_PUNTOS_TOTALES
                },
                null, null, null, null,
                AdminSQLiteOpenHelper.COLUMN_PUNTOS_TOTALES + " DESC"
        );

        int posicion = 1;

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(0);
                int puntos = cursor.getInt(1);

                // Crear vista para cada usuario
                agregarUsuarioAlRanking(posicion, nombre, puntos);
                posicion++;

            } while (cursor.moveToNext());
        } else {
            // Si no hay usuarios
            TextView tvVacio = new TextView(this);
            tvVacio.setText("No hay usuarios registrados");
            tvVacio.setTextSize(18);
            tvVacio.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvVacio.setPadding(20, 20, 20, 20);
            rankingContainer.addView(tvVacio);
        }

        cursor.close();
        db.close();
    }

    private void agregarUsuarioAlRanking(int posicion, String nombre, int puntos) {
        // Crear LinearLayout horizontal para cada entrada
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(30, 20, 30, 20);
        itemLayout.setBackgroundResource(R.drawable.ranking_item_background);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(20, 10, 20, 10);
        itemLayout.setLayoutParams(layoutParams);

        // TextView para la posición
        TextView tvPosicion = new TextView(this);
        tvPosicion.setText(posicion + ".");
        tvPosicion.setTextSize(20);
        tvPosicion.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvPosicion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvPosicion.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.15f
        ));

        // TextView para el nombre
        TextView tvNombre = new TextView(this);
        tvNombre.setText(nombre);
        tvNombre.setTextSize(18);
        tvNombre.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvNombre.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.60f
        ));

        // TextView para los puntos
        TextView tvPuntos = new TextView(this);
        tvPuntos.setText(puntos + " pts");
        tvPuntos.setTextSize(18);
        tvPuntos.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
        tvPuntos.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        tvPuntos.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.25f
        ));

        // Agregar los TextViews al layout del item
        itemLayout.addView(tvPosicion);
        itemLayout.addView(tvNombre);
        itemLayout.addView(tvPuntos);

        // Agregar el item al contenedor principal
        rankingContainer.addView(itemLayout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}