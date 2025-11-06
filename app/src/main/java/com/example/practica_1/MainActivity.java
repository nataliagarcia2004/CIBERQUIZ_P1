package com.example.practica_1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private AdminSQLiteOpenHelper admin;

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

        // Inicializar el administrador de base de datos
        admin = new AdminSQLiteOpenHelper(this);

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
        return password.length() >= 6;
    }

    // ==================== HASHEAR CONTRASEÑA ====================

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password; // En caso de error, devolver password sin hashear
        }
    }

    // ==================== LOGIN ====================

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

        // Abrir base de datos en modo lectura
        SQLiteDatabase db = admin.getReadableDatabase();

        // Hashear la contraseña ingresada
        String hashedPassword = hashPassword(password);

        // Consultar si existe el usuario con ese email y contraseña
        Cursor cursor = db.query(
                AdminSQLiteOpenHelper.TABLE_USUARIOS,
                new String[]{AdminSQLiteOpenHelper.COLUMN_EMAIL,
                        AdminSQLiteOpenHelper.COLUMN_NOMBRE,
                        AdminSQLiteOpenHelper.COLUMN_PUNTOS_TOTALES},
                AdminSQLiteOpenHelper.COLUMN_EMAIL + "=? AND " +
                        AdminSQLiteOpenHelper.COLUMN_PASSWORD + "=?",
                new String[]{email, hashedPassword},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            // Usuario encontrado - Login exitoso
            String nombreUsuario = cursor.getString(1);
            int puntosTotales = cursor.getInt(2);

            cursor.close();
            db.close();

            mostrarSnackbarExito(view, "¡Bienvenido " + nombreUsuario + "!");

            view.postDelayed(() -> {
                Intent i = new Intent(this, Menu.class);
                i.putExtra("email", email);
                i.putExtra("nombre", nombreUsuario);
                i.putExtra("puntos_totales", puntosTotales);
                i.putExtra("puntuacion", 0); // Puntuación de la ronda actual
                startActivity(i);
                overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
            }, 1500);
        } else {
            // Usuario no encontrado o contraseña incorrecta
            cursor.close();
            db.close();
            mostrarSnackbarError(view, "Credenciales incorrectas");
        }
    }

    // ==================== REGISTRO ====================

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

        // Abrir base de datos en modo escritura
        SQLiteDatabase db = admin.getWritableDatabase();

        // Verificar si el email ya existe
        Cursor cursor = db.query(
                AdminSQLiteOpenHelper.TABLE_USUARIOS,
                new String[]{AdminSQLiteOpenHelper.COLUMN_EMAIL},
                AdminSQLiteOpenHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            // El email ya está registrado
            cursor.close();
            db.close();
            mostrarSnackbarError(view, "Este correo ya está registrado");
            return;
        }
        cursor.close();

        // Hashear la contraseña
        String hashedPassword = hashPassword(password);

        // Insertar nuevo usuario
        ContentValues registro = new ContentValues();
        registro.put(AdminSQLiteOpenHelper.COLUMN_NOMBRE, nombre);
        registro.put(AdminSQLiteOpenHelper.COLUMN_EMAIL, email);
        registro.put(AdminSQLiteOpenHelper.COLUMN_PASSWORD, hashedPassword);
        registro.put(AdminSQLiteOpenHelper.COLUMN_PUNTOS_TOTALES, 0);

        long resultado = db.insert(AdminSQLiteOpenHelper.TABLE_USUARIOS, null, registro);
        db.close();

        if (resultado != -1) {
            // Registro exitoso
            mostrarSnackbarExito(view, "¡Cuenta creada exitosamente! Iniciando sesión...");

            // Limpiar campos
            editTextRegisterName.setText("");
            editTextRegisterEmail.setText("");
            editTextRegisterPassword.setText("");
            editTextRegisterConfirmPassword.setText("");

            view.postDelayed(() -> {
                Intent i = new Intent(this, Menu.class);
                i.putExtra("email", email);
                i.putExtra("nombre", nombre);
                i.putExtra("puntos_totales", 0);
                i.putExtra("puntuacion", 0);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in_zoom, R.anim.fade_out_zoom);
            }, 1500);
        } else {
            mostrarSnackbarError(view, "Error al crear la cuenta. Intenta de nuevo.");
        }
    }
}