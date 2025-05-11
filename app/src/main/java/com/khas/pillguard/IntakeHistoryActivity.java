package com.khas.pillguard;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IntakeHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerHistory;
    private IntakeHistoryAdapter adapter;
    private List<IntakeHistoryModel> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake_history);

        recyclerHistory = findViewById(R.id.recyclerHistory);
        adapter = new IntakeHistoryAdapter(historyList);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerHistory.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:5000/medications");
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
                List<IntakeHistoryModel> fetchedList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String patient = obj.optString("patient", "Unknown");
                    String medication = obj.optString("medication", "-");
                    JSONArray daysArray = obj.optJSONArray("days");
                    String time = obj.optString("time", "-");

                    boolean taken = (i % 2 == 0);

                    fetchedList.add(new IntakeHistoryModel(patient, medication, "Unknown Day(s)", time, taken));
                }

                runOnUiThread(() -> {
                    historyList.clear();
                    historyList.addAll(fetchedList);
                    adapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to fetch intake history", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
