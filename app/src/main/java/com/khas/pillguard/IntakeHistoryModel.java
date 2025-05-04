package com.khas.pillguard;

public class IntakeHistoryModel {
    private String patientName;
    private String medicineName;
    private String date;
    private String time;
    private boolean taken;

    public IntakeHistoryModel(String patientName, String medicineName, String date, String time, boolean taken) {
        this.patientName = patientName;
        this.medicineName = medicineName;
        this.date = date;
        this.time = time;
        this.taken = taken;
    }

    public String getPatientName() { return patientName; }
    public String getMedicineName() { return medicineName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public boolean isTaken() { return taken; }
}
