package com.hospital.model;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private String password;

    // Default constructor
    public Doctor() {}

    // Parameterized constructor (without doctorId)
    public Doctor(String name, String specialization, String phone, String email, String password) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}