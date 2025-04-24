package com.khas.pillguard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PillScheduleActivity extends AppCompatActivity {

    private RecyclerView rvSchedule;
    private List<Medication> medicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_schedule);

        // Initialize RecyclerView
        rvSchedule = findViewById(R.id.rvSchedule);

        // Set up RecyclerView with mock data
        medicationList = generateMockScheduleData();
        MedicationAdapter adapter = new MedicationAdapter(medicationList);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));
        rvSchedule.setAdapter(adapter);
    }

    // Method to generate mock schedule data
    private List<Medication> generateMockScheduleData() {
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication("Medication A", "08:00 AM", "Taken"));
        medications.add(new Medication("Medication B", "01:00 PM", "Upcoming"));
        medications.add(new Medication("Medication C", "06:00 PM", "Upcoming"));
        medications.add(new Medication("Medication D", "10:00 PM", "Upcoming"));
        medications.add(new Medication("Medication A", "08:00 AM (Tomorrow)", "Scheduled"));
        medications.add(new Medication("Medication B", "01:00 PM (Tomorrow)", "Scheduled"));
        medications.add(new Medication("Medication C", "06:00 PM (Tomorrow)", "Scheduled"));
        medications.add(new Medication("Medication D", "10:00 PM (Tomorrow)", "Scheduled"));
        return medications;
    }
} 