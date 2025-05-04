package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khas.pillguard.adapters.MedicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnViewSchedule;
    private Button btnUnlockBox;
    private RecyclerView rvUpcomingMeds;
    private List<Medication> medicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnViewSchedule = findViewById(R.id.btnViewSchedule);
        btnUnlockBox = findViewById(R.id.btnUnlockBox);
        rvUpcomingMeds = findViewById(R.id.rvUpcomingMeds);

        tvWelcome.setText(getString(R.string.welcome_message, "User"));

        medicationList = generateMockData();
        MedicationAdapter adapter = new MedicationAdapter(medicationList);
        rvUpcomingMeds.setLayoutManager(new LinearLayoutManager(this));
        rvUpcomingMeds.setAdapter(adapter);

        btnViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PillScheduleActivity.class);
                startActivity(intent);
            }
        });

        btnUnlockBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, UnlockBoxActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Medication> generateMockData() {
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication("Medication A", "08:00 AM", "Upcoming"));
        medications.add(new Medication("Medication B", "01:00 PM", "Upcoming"));
        medications.add(new Medication("Medication C", "06:00 PM", "Upcoming"));
        return medications;
    }
} 