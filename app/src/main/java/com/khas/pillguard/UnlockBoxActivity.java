package com.khas.pillguard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UnlockBoxActivity extends AppCompatActivity {

    private EditText etUnlockPassword;
    private Button btnUnlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_box);

        // Initialize UI components
        etUnlockPassword = findViewById(R.id.etUnlockPassword);
        btnUnlock = findViewById(R.id.btnUnlock);

        // Set click listener for unlock button
        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etUnlockPassword.getText().toString().trim();

                // Simple validation
                if (password.isEmpty()) {
                    Toast.makeText(UnlockBoxActivity.this, R.string.fields_empty, Toast.LENGTH_SHORT).show();
                } else {
                    // Simulate unlock request sent (will be replaced with actual API call later)
                    Toast.makeText(UnlockBoxActivity.this, R.string.unlock_request_sent, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
} 