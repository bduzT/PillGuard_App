package com.khas.pillguard;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ManageScheduleActivity extends AppCompatActivity {

    private Spinner spinnerPatients;
    private EditText editMedicationName;
    private Button btnScanBarcode, btnPickTime, btnAssignMedication;
    private TextView tvSelectedTime;
    private RecyclerView recyclerAssignments;
    private MedicationAssignmentAdapter adapter;
    private List<MedicationAssignmentModel> assignmentList = new ArrayList<>();

    private CheckBox checkMon, checkTue, checkWed, checkThu, checkFri, checkSat, checkSun;

    private String selectedTime = "Not selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        spinnerPatients = findViewById(R.id.spinnerPatients);
        editMedicationName = findViewById(R.id.editMedicationName);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        btnPickTime = findViewById(R.id.btnPickTime);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        btnAssignMedication = findViewById(R.id.btnAssignMedication);
        recyclerAssignments = findViewById(R.id.recyclerAssignments);

        checkMon = findViewById(R.id.checkMon);
        checkTue = findViewById(R.id.checkTue);
        checkWed = findViewById(R.id.checkWed);
        checkThu = findViewById(R.id.checkThu);
        checkFri = findViewById(R.id.checkFri);
        checkSat = findViewById(R.id.checkSat);
        checkSun = findViewById(R.id.checkSun);

        ArrayAdapter<String> patientAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("Ali Yılmaz", "Ayşe Demir", "Mehmet Kara"));
        patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatients.setAdapter(patientAdapter);

        btnPickTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(ManageScheduleActivity.this,
                    (view, hourOfDay, minute1) -> {
                        selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                        tvSelectedTime.setText("Selected time: " + selectedTime);
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        btnScanBarcode.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(ManageScheduleActivity.this);
            integrator.setPrompt("Scan a medication barcode");
            integrator.setBeepEnabled(true);
            integrator.setOrientationLocked(true);
            integrator.initiateScan();
        });

        btnAssignMedication.setOnClickListener(v -> {
            String patient = spinnerPatients.getSelectedItem().toString();
            String medication = editMedicationName.getText().toString().trim();
            List<String> selectedDays = getSelectedDays();

            if (medication.isEmpty() || selectedTime.equals("Not selected") || selectedDays.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            saveMedicationToServer(patient, medication, selectedDays, selectedTime);

            assignmentList.add(new MedicationAssignmentModel(patient, medication, selectedDays, selectedTime));
            adapter.notifyDataSetChanged();

            editMedicationName.setText("");
            selectedTime = "Not selected";
            tvSelectedTime.setText("No time selected");
            clearDaySelections();
        });

        adapter = new MedicationAssignmentAdapter(assignmentList);
        recyclerAssignments.setLayoutManager(new LinearLayoutManager(this));
        recyclerAssignments.setAdapter(adapter);
    }

    private void saveMedicationToServer(String patient, String medication, List<String> days, String time) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:5000/medication");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("patient", patient);
                json.put("medication", medication);
                json.put("days", new JSONArray(days));
                json.put("time", time);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(this, "Medication assigned successfully!", Toast.LENGTH_SHORT).show();
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

    private List<String> getSelectedDays() {
        List<String> days = new ArrayList<>();
        if (checkMon.isChecked()) days.add("Mon");
        if (checkTue.isChecked()) days.add("Tue");
        if (checkWed.isChecked()) days.add("Wed");
        if (checkThu.isChecked()) days.add("Thu");
        if (checkFri.isChecked()) days.add("Fri");
        if (checkSat.isChecked()) days.add("Sat");
        if (checkSun.isChecked()) days.add("Sun");
        return days;
    }

    private void clearDaySelections() {
        checkMon.setChecked(false);
        checkTue.setChecked(false);
        checkWed.setChecked(false);
        checkThu.setChecked(false);
        checkFri.setChecked(false);
        checkSat.setChecked(false);
        checkSun.setChecked(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                editMedicationName.setText(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
