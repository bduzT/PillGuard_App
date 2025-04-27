package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddNurseActivity extends AppCompatActivity {

    private EditText etNurseName, etNurseSurname, etNurseAge, etNurseDOB;
    private RadioGroup radioGroupGender;
    private Button btnUploadPhoto, btnAddNurse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nurse);

        etNurseName = findViewById(R.id.etNurseName);
        etNurseSurname = findViewById(R.id.etNurseSurname);
        etNurseDOB = findViewById(R.id.etNurseDOB);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto);
        btnAddNurse = findViewById(R.id.btnAddNurse);

        btnAddNurse.setOnClickListener(v -> {
            String name = etNurseName.getText().toString().trim();
            String surname = etNurseSurname.getText().toString().trim();
            String age = etNurseAge.getText().toString().trim();
            String dob = etNurseDOB.getText().toString().trim();
            int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
            RadioButton selectedGender = findViewById(selectedGenderId);
            String gender = selectedGender != null ? selectedGender.getText().toString() : "Not Specified";

            if (name.isEmpty() || surname.isEmpty() || age.isEmpty() || dob.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(AddNurseActivity.this, UserListActivity.class);
                intent.putExtra("nurseName", name);
                intent.putExtra("nurseSurname", surname);
                intent.putExtra("nurseAge", age);
                intent.putExtra("nurseDOB", dob);
                intent.putExtra("nurseGender", gender);

                startActivity(intent);
            }
        });
    }
}
