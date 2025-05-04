package com.khas.pillguard;

import java.util.List;

public class MedicationAssignmentModel {
    private String patientName;
    private String medicationName;
    private List<String> days;
    private String time;

    public MedicationAssignmentModel(String patientName, String medicationName, List<String> days, String time) {
        this.patientName = patientName;
        this.medicationName = medicationName;
        this.days = days;
        this.time = time;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public List<String> getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }
}
