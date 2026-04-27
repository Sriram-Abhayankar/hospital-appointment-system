package com.hospital.model;

public class Appointment {
private int appointmentId;
private int patientId;
private int doctorId;
private int slotId;
private String status;
private String bookedAt;

// Default constructor
public Appointment() {}

// Parameterized constructor (without appointmentId)
public Appointment(int patientId, int doctorId, int slotId, String status, String bookedAt) {
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.slotId = slotId;
    this.status = status;
    this.bookedAt = bookedAt;
}

// Getters and setters
public int getAppointmentId() {
    return appointmentId;
}

public void setAppointmentId(int appointmentId) {
    this.appointmentId = appointmentId;
}

public int getPatientId() {
    return patientId;
}

public void setPatientId(int patientId) {
    this.patientId = patientId;
}

public int getDoctorId() {
    return doctorId;
}

public void setDoctorId(int doctorId) {
    this.doctorId = doctorId;
}

public int getSlotId() {
    return slotId;
}

public void setSlotId(int slotId) {
    this.slotId = slotId;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

public String getBookedAt() {
    return bookedAt;
}

public void setBookedAt(String bookedAt) {
    this.bookedAt = bookedAt;
}
}