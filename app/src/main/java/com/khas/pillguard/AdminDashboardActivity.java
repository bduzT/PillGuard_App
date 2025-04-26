package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnUserList, btnAssignNurses, btnScheduleLogData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnUserList = findViewById(R.id.btnUserList);
        btnAssignNurses = findViewById(R.id.btnAssignNurses);
        btnScheduleLogData = findViewById(R.id.btnScheduleLogData);

        btnUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });

        btnAssignNurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AssignNursesActivity.class);
                startActivity(intent);
            }
        });

        btnScheduleLogData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, ScheduleLogDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
