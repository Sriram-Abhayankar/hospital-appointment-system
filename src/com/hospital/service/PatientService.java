package com.hospital.service;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import java.util.Scanner;

public class PatientService {
    private PatientDAO patientDAO;

    public PatientService() {
        patientDAO = new PatientDAO();
    }

    public void registerPatient(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = Integer.parseInt(sc.nextLine());
        System.out.print("Enter gender: ");
        String gender = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        // Normally you'd generate patientId in the DB, but assuming manual for now
        int patientId = 0; // Placeholder, adjust if needed

        Patient patient = new Patient();
        patient.setPatientId(patientId);
        patient.setName(name);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setEmail(email);
        patient.setPassword(password);

        boolean registered = patientDAO.registerPatient(patient);
        if (registered) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("Patient registration failed.");
        }
    }

    public Patient loginPatient(Scanner sc) {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Patient patient = patientDAO.loginPatient(email, password);
        if (patient != null) {
            System.out.println("Login successful. Welcome, " + patient.getName() + "!");
        } else {
            System.out.println("Login failed. Invalid email or password.");
        }
        return patient;
    }
}