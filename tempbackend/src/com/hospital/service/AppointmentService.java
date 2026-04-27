package com.hospital.service;
import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.SlotDAO;

import java.util.Scanner;

public class AppointmentService {
    // 1. View appointments for a doctor
    public void viewDoctorAppointments(int doctorId) {
        appointmentDAO.viewDoctorAppointments(doctorId);
    }

    // 2. Update appointment status for a doctor
    public void updateAppointmentStatus(Scanner sc, int doctorId) {
        System.out.print("Enter Appointment ID to update: ");
        int appointmentId;
        try {
            appointmentId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Appointment ID.");
            return;
        }

        System.out.print("Enter new status (Approved/Completed/Rejected): ");
        String newStatus = sc.nextLine().trim();

        boolean success = appointmentDAO.updateAppointmentStatus(appointmentId, doctorId, newStatus);
        if (success) {
            System.out.println("Appointment status updated successfully.");
        } else {
            System.out.println("Failed to update appointment status.");
        }
    }
    private AppointmentDAO appointmentDAO;
    private SlotDAO slotDAO;

    public AppointmentService() {
        this.appointmentDAO = new AppointmentDAO();
        this.slotDAO = new SlotDAO();
    }

    // 1. Book Appointment
    public void bookAppointment(Scanner sc, int patientId) {
        System.out.print("Enter Doctor ID to book appointment with: ");
        int doctorId;
        try {
            doctorId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Doctor ID. Booking cancelled.");
            return;
        }

        // Show available slots for this doctor
        System.out.println("--- Available Slots for Doctor ID " + doctorId + " ---");
        slotDAO.viewAvailableSlotsByDoctor(doctorId);

        System.out.print("Enter Slot ID to book: ");
        int slotId;
        try {
            slotId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Slot ID. Booking cancelled.");
            return;
        }

        boolean booked = appointmentDAO.bookAppointment(patientId, doctorId, slotId);
        if (booked) {
            System.out.println("Appointment booked successfully!");
        } else {
            System.out.println("Failed to book appointment. Please check if the slot is still available.");
        }
    }

    // 2. View My Appointments
    public void viewMyAppointments(int patientId) {
        appointmentDAO.viewPatientAppointments(patientId);
    }

    // 3. Cancel Appointment
    public void cancelAppointment(Scanner sc, int patientId) {
        System.out.print("Enter Appointment ID to cancel: ");
        int appointmentId;
        try {
            appointmentId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Appointment ID. Cancellation aborted.");
            return;
        }

        boolean cancelled = appointmentDAO.cancelAppointment(appointmentId, patientId);
        if (cancelled) {
            System.out.println("Appointment cancelled successfully.");
        } else {
            System.out.println("Failed to cancel appointment. Check if the appointment ID is correct and belongs to you.");
        }
    }
}