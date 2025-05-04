package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.khas.pillguard.helpers.DatabaseControl;

import android.database.Cursor;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private DatabaseControl dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI bileşenlerini tanımla
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        dbHelper = new DatabaseControl(this);

        try {
            dbHelper.createDatabase();
            dbHelper.openDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.fields_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.equals("nurse") && password.equals("nurse")) {
                    Toast.makeText(LoginActivity.this, "Nurse girişi başarılı!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, NurseInterfaceActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                        "SELECT * FROM Admin WHERE Username = ? AND Password = ?",
                        new String[]{email, password}
                );

                if (cursor != null && cursor.moveToFirst()) {
                    Toast.makeText(LoginActivity.this, "Admin girişi başarılı!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Hatalı kullanıcı adı veya şifre!", Toast.LENGTH_SHORT).show();
                }

                if (cursor != null) {
                    cursor.close();
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.password_reset_message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
