package com.hospital.service;
import com.hospital.model.Patient;
import java.util.Scanner;

public class PatientDashboard {

    private DoctorService doctorService;
    private AppointmentService appointmentService;

    public PatientDashboard() {
        this.doctorService = new DoctorService();
        this.appointmentService = new AppointmentService();
    }

    public void patientMenu(Scanner sc, Patient patient) {
        boolean running = true;
        while (running) {
            System.out.println("\n------ Patient Dashboard ------");
            System.out.println("1. View All Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. View My Appointments");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    doctorService.viewAllDoctors();
                    break;
                case "2":
                    appointmentService.bookAppointment(sc, patient.getPatientId());
                    break;
                case "3":
                    appointmentService.viewMyAppointments(patient.getPatientId());
                    break;
                case "4":
                    appointmentService.cancelAppointment(sc, patient.getPatientId());
                    break;
                case "5":
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
