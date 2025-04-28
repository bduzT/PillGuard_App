package com.khas.pillguard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.khas.pillguard.adapters.UserAdapter;
import com.khas.pillguard.models.User;
import com.khas.pillguard.helpers.DatabaseControl;
import java.util.ArrayList;
import java.util.Collections;

public class UserListActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> userList;
    private DatabaseControl dbHelper;
    private Button btnAddNurse;
    private RadioGroup radioGroupUserType;
    private Spinner spinnerSort;

    private String currentFilter = "All";
    private String currentSort = "None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddNurse = findViewById(R.id.btnAddNurse);
        radioGroupUserType = findViewById(R.id.radioGroupUserType);
        spinnerSort = findViewById(R.id.spinnerSort);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);

        dbHelper = new DatabaseControl(this);

        btnAddNurse.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, AddNurseActivity.class);
            startActivity(intent);
        });

        radioGroupUserType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioAll) {
                currentFilter = "All";
            } else if (checkedId == R.id.radioPatients) {
                currentFilter = "Patients";
            } else if (checkedId == R.id.radioNurses) {
                currentFilter = "Nurses";
            }
            loadUsers();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(adapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSort = parent.getItemAtPosition(position).toString();
                applySorting();
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        loadUsers();
    }

    private void loadUsers() {
        try {
            dbHelper.openDatabase();

            userList.clear();

            if (currentFilter.equals("Nurses")) {
                loadNurses();
            } else if (currentFilter.equals("Patients")) {
                loadPatients();
            } else {
                loadNurses();
                loadPatients();
            }

            applySorting();
            userAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }


    private void loadNurses() {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT CaregiverID, FirstName, LastName, DateOfBirth, Photo FROM Caregiver", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String dateOfBirth = cursor.getString(3);
                byte[] photo = cursor.getBlob(4);

                String fullName = firstName + " " + lastName;
                userList.add(new User(id, fullName, dateOfBirth, photo));
            } while (cursor.moveToNext());
        }
    }

    private void loadPatients() {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT PatientID, FirstName, LastName, DateOfBirth, Photo FROM Patient", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String dateOfBirth = cursor.getString(3);
                byte[] photo = cursor.getBlob(4);

                String fullName = firstName + " " + lastName;
                userList.add(new User(id, fullName, dateOfBirth, photo));
            } while (cursor.moveToNext());
        }
    }


    private void applySorting() {
        if (currentSort.equals("A-Z")) {
            Collections.sort(userList, (u1, u2) -> u1.getFullName().compareToIgnoreCase(u2.getFullName()));
        } else if (currentSort.equals("Z-A")) {
            Collections.sort(userList, (u1, u2) -> u2.getFullName().compareToIgnoreCase(u1.getFullName()));
        }
    }

    @Override
    public void onEditClick(User user) {
        Intent intent = new Intent(UserListActivity.this, EditUserActivity.class);
        intent.putExtra("userId", user.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int userId) {
        dbHelper.openDatabase();
        dbHelper.deleteUser(userId);
        loadUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }
}
