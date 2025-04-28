package com.khas.pillguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class AddPatientActivity extends AppCompatActivity {

    private EditText inputFirstName, inputLastName, inputID, inputDOB;
    private RadioGroup genderGroup;
    private Button btnSavePatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputID = findViewById(R.id.inputID);
        inputDOB = findViewById(R.id.inputDOB);
        genderGroup = findViewById(R.id.genderGroup);
        btnSavePatient = findViewById(R.id.btnSavePatient);

        btnSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = inputFirstName.getText().toString();
                String lastName = inputLastName.getText().toString();
                String id = inputID.getText().toString();
                String dob = inputDOB.getText().toString();
                int selectedGenderId = genderGroup.getCheckedRadioButtonId();
                RadioButton genderButton = findViewById(selectedGenderId);
                String gender = genderButton != null ? genderButton.getText().toString() : "Not Selected";

                Intent intent = new Intent(AddPatientActivity.this, UserListActivity.class);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("id", id);
                intent.putExtra("dob", dob);
                intent.putExtra("gender", gender);
                intent.putExtra("type", "Patient");
                startActivity(intent);
            }
        });
    }
}
