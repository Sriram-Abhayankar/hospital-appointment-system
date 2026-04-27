package com.hospital.service;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;
import java.util.Scanner;

public class DoctorService {
    private DoctorDAO doctorDAO;

    public DoctorService() {
        this.doctorDAO = new DoctorDAO();
    }

    public void addDoctor(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Doctor doctor = new Doctor(name, specialization, phone, email, password);
        boolean added = doctorDAO.addDoctor(doctor);
        if (added) {
            System.out.println("Doctor added successfully.");
        } else {
            System.out.println("Doctor addition failed.");
        }
    }

    public Doctor loginDoctor(Scanner sc) {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Doctor doctor = doctorDAO.loginDoctor(email, password);
        if (doctor != null) {
            System.out.println("Login successful. Welcome, " + doctor.getName() + "!");
        } else {
            System.out.println("Login failed. Invalid email or password.");
        }
        return doctor;
    }

    public void viewAllDoctors() {
        doctorDAO.viewAllDoctors();
    }
}
    