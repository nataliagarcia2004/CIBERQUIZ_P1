package com.example.practica_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        snackbar.setBackgroundTint(Color.parseColor("#FF9800"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    // Método para mostrar Snackbar de error (rojo)
    private void mostrarSnackbarError(View view, String mensaje) {
        Snackbar snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#F44336"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    // Método para mostrar Snackbar de éxito (verde)
    private void mostrarSnackbarExito(View view, String mensaje) {
        Snackbar snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#4CAF50"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    // Método para validar el formato del email
    private boolean esEmailValido(String email) {
        // Patrón: algo@algo.algo (acepta .es, .com, .org, etc.)
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
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

        // Validar formato del email
        if (!esEmailValido(email)) {
            mostrarSnackbarError(view, "❌ El correo electrónico no tiene un formato válido");
            return;
        }

        // Si todo está correcto, mostrar mensaje de éxito
        mostrarSnackbarExito(view, "✅ Sesión iniciada correctamente");

        // Ir al menú después de un pequeño delay para que se vea el Snackbar
        view.postDelayed(() -> {
            Intent i = new Intent(this, Menu.class);
            i.putExtra("puntuacion", puntuacion);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
        }, 1500); // Espera 1.5 segundos antes de cambiar de actividad
    }
}