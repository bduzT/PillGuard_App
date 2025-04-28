package com.khas.pillguard.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseControl extends SQLiteOpenHelper {

    private static String DB_NAME = "pillguard.db";
    private static String DB_PATH = "";
    private SQLiteDatabase myDatabase;
    private final Context myContext;

    public DatabaseControl(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    public void createDatabase() throws IOException {
        File dbFile = new File(DB_PATH);
        if (!dbFile.exists()) {
            this.getReadableDatabase();
            copyDatabase();
        }
    }


    private void deleteOldDatabase() {
        File dbFile = new File(DB_PATH);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    private void copyDatabase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase() throws SQLException {
        myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDatabase != null) {
            myDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insertNurse(String firstName, String lastName, String contactInfo, String dateOfBirth) {
        if (myDatabase == null || !myDatabase.isOpen()) {
            openDatabase();
        }

        myDatabase.beginTransaction();
        try {
            String sql = "INSERT INTO Caregiver (FirstName, LastName, ContactInfo, DateOfBirth, AdminID) VALUES (?, ?, ?, ?, ?)";
            Object[] bindArgs = new Object[]{firstName, lastName, contactInfo, dateOfBirth, 1};
            myDatabase.execSQL(sql, bindArgs);

            myDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myDatabase.endTransaction();
        }
    }

    public void deleteUser(int userId) {
        myDatabase.delete("Caregiver", "CaregiverID = ?", new String[]{String.valueOf(userId)});
    }
    public void updateNurse(int userId, String firstName, String lastName, String contactInfo, String dateOfBirth, byte[] photoBytes) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (photoBytes != null) {
            String sql = "UPDATE Caregiver SET FirstName = ?, LastName = ?, ContactInfo = ?, DateOfBirth = ?, Photo = ? WHERE CaregiverID = ?";
            Object[] bindArgs = new Object[]{firstName, lastName, contactInfo, dateOfBirth, photoBytes, userId};
            db.execSQL(sql, bindArgs);
        } else {
            String sql = "UPDATE Caregiver SET FirstName = ?, LastName = ?, ContactInfo = ?, DateOfBirth = ? WHERE CaregiverID = ?";
            Object[] bindArgs = new Object[]{firstName, lastName, contactInfo, dateOfBirth, userId};
            db.execSQL(sql, bindArgs);
        }

        db.close();
    }

    public void insertNurse(String firstName, String lastName, String contactInfo, String dateOfBirth, byte[] photoBytes) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO Caregiver (FirstName, LastName, ContactInfo, DateOfBirth, Photo, AdminID) VALUES (?, ?, ?, ?, ?, ?)";

        Object[] bindArgs = new Object[]{
                firstName,
                lastName,
                contactInfo,
                dateOfBirth,
                photoBytes,
                1
        };

        db.execSQL(sql, bindArgs);
        db.close();
    }



}
