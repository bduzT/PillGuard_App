package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.khas.pillguard.adapters.UserAdapter;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    private Button btnAddPatient, btnAddNurse;
    private RecyclerView recyclerViewUserList;
    private ArrayList<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        btnAddPatient = findViewById(R.id.btnAddPatient);
        btnAddNurse = findViewById(R.id.btnAddNurse);
        recyclerViewUserList = findViewById(R.id.recyclerViewUserList);

        recyclerViewUserList.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String gender = intent.getStringExtra("gender");
        String type = intent.getStringExtra("type");  // Hasta tipi
        String patientInfo = firstName + " " + lastName + " (" + gender + ") - " + type;

        if (firstName != null && lastName != null) {
            userList.add(patientInfo);
        }

        UserAdapter userAdapter = new UserAdapter(userList, new UserAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Toast.makeText(UserListActivity.this, "Edit clicked for " + userList.get(position), Toast.LENGTH_SHORT).show();
                String patientInfo = userList.get(position);
                Intent intent = new Intent(UserListActivity.this, AddPatientActivity.class);
                intent.putExtra("patientInfo", patientInfo);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                userList.remove(position);
                userAdapter.notifyItemRemoved(position);
                Toast.makeText(UserListActivity.this, "Patient deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewUserList.setAdapter(userAdapter);

        btnAddPatient.setOnClickListener(v -> {
            Intent intent1 = new Intent(UserListActivity.this, AddPatientActivity.class);
            startActivity(intent1);
        });

        btnAddNurse.setOnClickListener(v -> {
            Intent intent1 = new Intent(UserListActivity.this, AddNurseActivity.class);
            startActivity(intent1);
        });
    }
}
