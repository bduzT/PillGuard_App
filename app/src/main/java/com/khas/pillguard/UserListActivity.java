package com.khas.pillguard;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khas.pillguard.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private Spinner spinnerSortOptions;
    private RecyclerView recyclerViewUserList;
    private UserAdapter userAdapter;
    private ArrayList<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        spinnerSortOptions = findViewById(R.id.spinnerSort);
        recyclerViewUserList = findViewById(R.id.recyclerViewUserList);

        recyclerViewUserList.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        userList.add("John Doe (Nurse)");
        userList.add("Jane Smith (Patient)");
        userList.add("Michael Johnson (Nurse)");

        userAdapter = new UserAdapter(userList, new UserAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Toast.makeText(UserListActivity.this, "Edit clicked for " + userList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                userList.remove(position);
                userAdapter.notifyItemRemoved(position);
                Toast.makeText(UserListActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewUserList.setAdapter(userAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortOptions.setAdapter(adapter);

        spinnerSortOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedSortOption = parentView.getItemAtPosition(position).toString();
                sortList(selectedSortOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    private void sortList(String sortOption) {
        if (sortOption.equals("Alphabetical (A-Z)")) {
            userList.sort(String::compareTo);
        } else if (sortOption.equals("Alphabetical (Z-A)")) {
            userList.sort((a, b) -> b.compareTo(a));
        } else if (sortOption.equals("Chronological")) {
            Toast.makeText(this, "Chronological sort (example)", Toast.LENGTH_SHORT).show();
        }
        userAdapter.notifyDataSetChanged();
    }
}
