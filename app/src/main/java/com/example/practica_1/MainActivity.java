package com.example.practica_1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private int puntuacion;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", puntuacion);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    // Método para mostrar Snackbar con advertencia (naranja/amarillo)
    private void mostrarSnackbarAdvertencia(View view, String mensaje) {
        Snackbar snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();

        // Fondo naranja/amarillo para advertencias
        snackbarView.setBackgroundColor(Color.parseColor("#FF9800"));
        snackbarView.setElevation(16f);

        // Personalizar el texto
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setMaxLines(3);

        snackbar.show();
    }

    public void Menu(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "⚠️ Por favor, ingresa tu correo electrónico");
            return;
        }

        if (password.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "⚠️ Por favor, ingresa tu contraseña");
            return;
        }

        Intent i = new Intent(this, Menu.class);
        i.putExtra("puntuacion", puntuacion);
        startActivity(i);
    }
}