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
import android.database.Cursor;

public class EditUserActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etContactInfo, etDateOfBirth;
    private Button btnUploadPhoto, btnSaveChanges;
    private ImageView ivPhotoPreview;
    private DatabaseControl dbHelper;
    private int userId;
    private static final int PICK_IMAGE_REQUEST = 1;
    private byte[] selectedImageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        etFirstName = findViewById(R.id.etEditFirstName);
        etLastName = findViewById(R.id.etEditLastName);
        etContactInfo = findViewById(R.id.etEditContactInfo);
        etDateOfBirth = findViewById(R.id.etEditDateOfBirth);
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        ivPhotoPreview = findViewById(R.id.ivPhotoPreview);

        dbHelper = new DatabaseControl(this);

        etFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), lettersOnlyFilter});
        etLastName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), lettersOnlyFilter});
        etContactInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        userId = getIntent().getIntExtra("userId", -1);

        if (userId != -1) {
            loadUserData(userId);
        }

        btnUploadPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnSaveChanges.setOnClickListener(v -> {
            saveChanges();
        });
    }

    private void loadUserData(int id) {
        try {
            dbHelper.openDatabase();

            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                    "SELECT FirstName, LastName, ContactInfo, DateOfBirth, Photo FROM Caregiver WHERE CaregiverID = ?",
                    new String[]{String.valueOf(id)}
            );

            if (cursor.moveToFirst()) {
                etFirstName.setText(cursor.getString(0));
                etLastName.setText(cursor.getString(1));
                etContactInfo.setText(cursor.getString(2));
                etDateOfBirth.setText(cursor.getString(3));

                byte[] photoBytes = cursor.getBlob(4);
                if (photoBytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                    ivPhotoPreview.setImageBitmap(bitmap);
                    selectedImageBytes = photoBytes;
                }
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    private void saveChanges() {
        String newFirstName = etFirstName.getText().toString().trim();
        String newLastName = etLastName.getText().toString().trim();
        String newContactInfo = etContactInfo.getText().toString().trim();
        String newDateOfBirth = etDateOfBirth.getText().toString().trim();

        if (newFirstName.isEmpty() || newLastName.isEmpty() || newDateOfBirth.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newFirstName.length() > 15 || newLastName.length() > 15) {
            Toast.makeText(this, "Name and Surname must be at most 15 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newDateOfBirth.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            Toast.makeText(this, "Date of Birth must be in format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newContactInfo.length() > 15) {
            Toast.makeText(this, "Contact Info must be at most 15 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.openDatabase();
        dbHelper.updateNurse(userId, newFirstName, newLastName, newContactInfo, newDateOfBirth, selectedImageBytes);
        dbHelper.close();

        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
        finish();
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
