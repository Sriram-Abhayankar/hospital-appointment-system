package com.hospital.main;

import java.util.Scanner;
import com.hospital.service.PatientService;
import com.hospital.model.Patient;
import com.hospital.service.DoctorDashboard;
import com.hospital.service.PatientDashboard;

public class HospitalApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientService patientService = new PatientService();
        com.hospital.service.DoctorService doctorService = new com.hospital.service.DoctorService();
        DoctorDashboard doctorDashboard = new DoctorDashboard();
        PatientDashboard patientDashboard = new PatientDashboard();
        while (true) {
            System.out.println("\n--- Hospital Management Menu ---");
            System.out.println("1. Patient Register");
            System.out.println("2. Patient Login");
            System.out.println("3. Admin - Add Doctor");
            System.out.println("4. Doctor Login");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            String input = sc.nextLine();
            int choice = -1;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                choice = -1;
            }
            
            switch (choice) {
                case 1:
                    patientService.registerPatient(sc);
                    break;
                case 2:
                    Patient patient = patientService.loginPatient(sc);
                    if (patient != null) {
                        System.out.println("Patient Dashboard coming next...");
                        patientDashboard.patientMenu(sc, patient);
                    }
                    break;
                case 3:
                    doctorService.addDoctor(sc);
                    break;
                case 4:
                    com.hospital.model.Doctor doctor = doctorService.loginDoctor(sc);
                    if (doctor != null) {
                        System.out.println("Doctor Dashboard coming next...");
                        doctorDashboard.doctorMenu(sc, doctor);
                    }
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}