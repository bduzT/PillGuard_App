package com.khas.pillguard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.khas.pillguard.helpers.DatabaseControl;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Pattern;

public class AddNurseActivity extends AppCompatActivity {

    private EditText etNurseName, etNurseSurname, etNurseContact, etNurseDOB;
    private Button btnUploadPhoto, btnAddNurse;
    private ImageView ivPhotoPreview;
    private DatabaseControl dbHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private byte[] selectedImageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nurse);

        etNurseName = findViewById(R.id.etNurseName);
        etNurseSurname = findViewById(R.id.etNurseSurname);
        etNurseContact = findViewById(R.id.etNurseContact);
        etNurseDOB = findViewById(R.id.etNurseDOB);
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto);
        btnAddNurse = findViewById(R.id.btnAddNurse);
        ivPhotoPreview = findViewById(R.id.ivPhotoPreview);
        dbHelper = new DatabaseControl(this);

        etNurseName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), lettersOnlyFilter});
        etNurseSurname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), lettersOnlyFilter});
        etNurseContact.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        btnUploadPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnAddNurse.setOnClickListener(v -> {
            String name = etNurseName.getText().toString().trim();
            String surname = etNurseSurname.getText().toString().trim();
            String contact = etNurseContact.getText().toString().trim();
            String dob = etNurseDOB.getText().toString().trim();

            if (name.isEmpty() || surname.isEmpty() || dob.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.length() > 15 || surname.length() > 15) {
                Toast.makeText(this, "Name and Surname must be at most 15 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!dob.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                Toast.makeText(this, "Date of Birth must be in format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contact.length() > 15) {
                Toast.makeText(this, "Contact Info must be at most 15 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.openDatabase();
            dbHelper.insertNurse(name, surname, contact, dob, selectedImageBytes);
            dbHelper.close();

            Toast.makeText(this, "Nurse added successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private InputFilter lettersOnlyFilter = new InputFilter() {
        Pattern pattern = Pattern.compile("[a-zA-ZğüşıöçĞÜŞİÖÇ ]+");

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (!pattern.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivPhotoPreview.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                selectedImageBytes = stream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
