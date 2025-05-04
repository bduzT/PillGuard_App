package com.khas.pillguard;

public class PatientModel {
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;

    public PatientModel(String firstName, String lastName, String dob, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDob() { return dob; }
    public String getGender() { return gender; }
}
