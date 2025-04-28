package com.khas.pillguard.models;

public class User {
    private int id;
    private String fullName;
    private String dateOfBirth;
    private byte[] photoBytes;

    public User(int id, String fullName, String dateOfBirth, byte[] photoBytes) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.photoBytes = photoBytes;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public byte[] getPhotoBytes() {
        return photoBytes;
    }
}
