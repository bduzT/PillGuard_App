package com.khas.pillguard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        historyList.add(new IntakeHistoryModel("Ali Yılmaz", "Parol 500mg", "2025-05-01", "08:00", true));
        historyList.add(new IntakeHistoryModel("Ali Yılmaz", "Parol 500mg", "2025-05-01", "14:00", false));
        historyList.add(new IntakeHistoryModel("Ali Yılmaz", "Parol 500mg", "2025-05-01", "20:00", true));
        adapter.notifyDataSetChanged();
    }
}
