package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khas.pillguard.adapters.PatientAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NurseInterfaceActivity extends AppCompatActivity {

    private Button btnAddPatient, btnViewSchedule, btnUnlockPillbox, btnIntakeHistory;
    private RecyclerView recyclerPatients;
    private List<PatientModel> patientList = new ArrayList<>();
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_interface);

        // Butonlar
        btnAddPatient = findViewById(R.id.btnAddPatient);
        btnViewSchedule = findViewById(R.id.btnViewSchedule);
        btnUnlockPillbox = findViewById(R.id.btnUnlockPillbox);
        btnIntakeHistory = findViewById(R.id.btnIntakeHistory);
        recyclerPatients = findViewById(R.id.recyclerPatients);

        // RecyclerView setup
        adapter = new PatientAdapter(patientList);
        recyclerPatients.setLayoutManager(new LinearLayoutManager(this));
        recyclerPatients.setAdapter(adapter);

        // Tuş olayları
        btnAddPatient.setOnClickListener(v -> {
            startActivity(new Intent(NurseInterfaceActivity.this, AddPatientActivity.class));
        });

        btnViewSchedule.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageScheduleActivity.class));
        });

        btnUnlockPillbox.setOnClickListener(v -> {
            startActivity(new Intent(this, UnlockActivity.class));
        });

        btnIntakeHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, IntakeHistoryActivity.class));
        });

        // Verileri çek
        fetchPatientsFromApi();
    }

    private void fetchPatientsFromApi() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:5000/patients");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                reader.close();

                JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
                List<PatientModel> fetchedPatients = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String first = obj.getString("first_name");
                    String last = obj.getString("last_name");
                    String dob = obj.getString("dob");
                    String gender = obj.getString("gender");

                    fetchedPatients.add(new PatientModel(first, last, dob, gender));
                }

                runOnUiThread(() -> {
                    patientList.clear();
                    patientList.addAll(fetchedPatients);
                    adapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Failed to fetch patients", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
