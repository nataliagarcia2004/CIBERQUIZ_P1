package com.example.practica_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private int puntuacion;

    // Pestañas
    private Button btnTabLogin, btnTabRegister;
    private LinearLayout loginContainer, registerContainer;

    // Campos de Login
    private EditText editTextLoginEmail, editTextLoginPassword;

    // Campos de Registro
    private EditText editTextRegisterName, editTextRegisterEmail;
    private EditText editTextRegisterPassword, editTextRegisterConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", puntuacion);

        // Inicializar pestañas
        btnTabLogin = findViewById(R.id.btnTabLogin);
        btnTabRegister = findViewById(R.id.btnTabRegister);

        // Inicializar contenedores
        loginContainer = findViewById(R.id.loginContainer);
        registerContainer = findViewById(R.id.registerContainer);

        // Inicializar campos de Login
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        // Inicializar campos de Registro
        editTextRegisterName = findViewById(R.id.editTextRegisterName);
        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextRegisterConfirmPassword = findViewById(R.id.editTextRegisterConfirmPassword);

        // Configurar listeners de pestañas
        btnTabLogin.setOnClickListener(v -> showLoginTab());
        btnTabRegister.setOnClickListener(v -> showRegisterTab());
    }

    private void showLoginTab() {
        loginContainer.setVisibility(View.VISIBLE);
        registerContainer.setVisibility(View.GONE);

        btnTabLogin.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_light));
        btnTabRegister.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
    }

    private void showRegisterTab() {
        loginContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.VISIBLE);

        btnTabLogin.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
        btnTabRegister.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_green_light));
    }

    // ==================== MÉTODOS PARA SNACKBAR ====================

    private void mostrarSnackbarAdvertencia(View view, String mensaje) {
        Snackbar snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#FF9800"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void mostrarSnackbarError(View view, String mensaje) {
        Snackbar snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#F44336"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void mostrarSnackbarExito(View view, String mensaje) {
        Snackbar snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#4CAF50"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    // ==================== VALIDACIONES ====================

    private boolean esEmailValido(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }

    private boolean esPasswordSegura(String password) {
        // Al menos 6 caracteres
        return password.length() >= 6;
    }

    // ==================== MÉTODO DE LOGIN ====================

    public void Menu(View view) {
        String email = editTextLoginEmail.getText().toString().trim();
        String password = editTextLoginPassword.getText().toString().trim();

        if (email.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "Por favor, ingresa tu correo electrónico");
            return;
        }

        if (password.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "Por favor, ingresa tu contraseña");
            return;
        }

        if (!esEmailValido(email)) {
            mostrarSnackbarError(view, "El formato del correo no es válido");
            return;
        }

        // TODO: Aquí validarás con la base de datos
        // Por ahora, simulamos login exitoso

        mostrarSnackbarExito(view, "Sesión iniciada correctamente");

        view.postDelayed(() -> {
            Intent i = new Intent(this, Menu.class);
            i.putExtra("puntuacion", puntuacion);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
        }, 1500);
    }

    // ==================== MÉTODO DE REGISTRO ====================

    public void RegistrarUsuario(View view) {
        String nombre = editTextRegisterName.getText().toString().trim();
        String email = editTextRegisterEmail.getText().toString().trim();
        String password = editTextRegisterPassword.getText().toString().trim();
        String confirmPassword = editTextRegisterConfirmPassword.getText().toString().trim();

        // Validación de campos vacíos
        if (nombre.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "Por favor, ingresa tu nombre");
            return;
        }

        if (email.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "Por favor, ingresa tu correo electrónico");
            return;
        }

        if (password.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "Por favor, ingresa una contraseña");
            return;
        }

        if (confirmPassword.isEmpty()) {
            mostrarSnackbarAdvertencia(view, "Por favor, confirma tu contraseña");
            return;
        }

        // Validación de formato de email
        if (!esEmailValido(email)) {
            mostrarSnackbarError(view, "El formato del correo no es válido");
            return;
        }

        // Validación de seguridad de contraseña
        if (!esPasswordSegura(password)) {
            mostrarSnackbarError(view, "La contraseña debe tener al menos 6 caracteres");
            return;
        }

        // Validación de que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            mostrarSnackbarError(view, "Las contraseñas no coinciden");
            return;
        }

        // TODO: Aquí guardarás el usuario en la base de datos
        // Por ahora, simulamos registro exitoso

        mostrarSnackbarExito(view, "¡Cuenta creada exitosamente! Iniciando sesión...");

        // Cambiar a la pestaña de login después del registro
        view.postDelayed(() -> {
            // Opcional: Puedes llevar directamente al menú o mostrar el login
            Intent i = new Intent(this, Menu.class);
            i.putExtra("puntuacion", 0); // Usuario nuevo empieza con 0 puntos
            startActivity(i);
            overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
        }, 1500);
    }
}