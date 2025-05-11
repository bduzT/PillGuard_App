package com.khas.pillguard;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class UnlockActivity extends AppCompatActivity {

    private Button btnUnlock;
    private Spinner spinnerPatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        btnUnlock = findViewById(R.id.btnUnlock);
        spinnerPatients = findViewById(R.id.spinnerPatients);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("Ali Yılmaz", "Ayşe Demir", "Mehmet Kara"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatients.setAdapter(adapter);

        btnUnlock.setOnClickListener(v -> {
            String patient = spinnerPatients.getSelectedItem().toString();
            sendUnlockCommand(patient);
        });
    }

    private void sendUnlockCommand(String patient) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:5000/unlock");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("patient", patient);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(this, "Pillbox remotely unlocked for " + patient, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Unlock failed: " + responseCode, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}
