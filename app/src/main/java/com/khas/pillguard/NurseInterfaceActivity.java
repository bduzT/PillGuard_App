package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khas.pillguard.adapters.PatientAdapter;

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

        btnAddPatient = findViewById(R.id.btnAddPatient);
        btnViewSchedule = findViewById(R.id.btnViewSchedule);
        btnUnlockPillbox = findViewById(R.id.btnUnlockPillbox);
        btnIntakeHistory = findViewById(R.id.btnIntakeHistory);
        recyclerPatients = findViewById(R.id.recyclerPatients);

        adapter = new PatientAdapter(patientList); // 👈 context parametresi kaldırıldı
        recyclerPatients.setLayoutManager(new LinearLayoutManager(this));
        recyclerPatients.setAdapter(adapter);


        btnAddPatient.setOnClickListener(v -> {
            Intent intent = new Intent(NurseInterfaceActivity.this, AddPatientActivity.class);
            startActivity(intent);
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
    }


}
