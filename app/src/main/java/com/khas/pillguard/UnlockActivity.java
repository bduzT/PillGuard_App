package com.khas.pillguard;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UnlockActivity extends AppCompatActivity {

    private Button btnUnlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        btnUnlock = findViewById(R.id.btnUnlock);

        btnUnlock.setOnClickListener(v -> {

            Toast.makeText(this, "Pillbox remotely unlocked!", Toast.LENGTH_SHORT).show();


        });
    }
}
