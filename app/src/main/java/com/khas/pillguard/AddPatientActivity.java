package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddPatientActivity extends AppCompatActivity {

    private EditText inputFirstName, inputLastName, inputID, inputDOB;
    private RadioGroup genderGroup;
    private Button btnSavePatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputID = findViewById(R.id.inputID);
        inputDOB = findViewById(R.id.inputDOB);
        genderGroup = findViewById(R.id.genderGroup);
        btnSavePatient = findViewById(R.id.btnSavePatient);

        btnSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = inputFirstName.getText().toString().trim();
                String lastName = inputLastName.getText().toString().trim();
                String id = inputID.getText().toString().trim();
                String dob = inputDOB.getText().toString().trim();
                int selectedGenderId = genderGroup.getCheckedRadioButtonId();
                RadioButton genderButton = findViewById(selectedGenderId);
                String gender = genderButton != null ? genderButton.getText().toString() : "";

                if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty() || dob.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(AddPatientActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendPatientToServer(firstName, lastName, id, dob, gender);
            }
        });
    }

    private void sendPatientToServer(String firstName, String lastName, String id, String dob, String gender) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:5000/patients");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("first_name", firstName);
                json.put("last_name", lastName);
                json.put("id", id);
                json.put("dob", dob);
                json.put("gender", gender);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode == 200 || responseCode == 201) {
                        Toast.makeText(this, "Patient added successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, NurseInterfaceActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Server error: " + responseCode, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}
