package com.hospital.service;

import com.hospital.model.Doctor;
import java.util.Scanner;

public class DoctorDashboard {
    private SlotService slotService;
    
    public DoctorDashboard() {
        this.slotService = new SlotService();
    }

    public void doctorMenu(Scanner sc, Doctor doctor) {
        while (true) {
            System.out.println("\n--- Doctor Dashboard ---");
            System.out.println("1. Add Availability Slot");
            System.out.println("2. View My Slots");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String input = sc.nextLine();
            int choice = -1;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }
            switch (choice) {
                case 1:
                    slotService.addSlot(sc, doctor.getDoctorId());
                    break;
                case 2:
                    slotService.viewDoctorSlots(doctor.getDoctorId());
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
