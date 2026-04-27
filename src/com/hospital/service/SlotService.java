package com.hospital.service;

import com.hospital.dao.SlotDAO;
import java.util.Scanner;

public class SlotService {
    private SlotDAO slotDAO;

    public SlotService() {
        this.slotDAO = new SlotDAO();
    }

    public void addSlot(Scanner sc, int doctorId) {
        System.out.print("Enter slot date (yyyy-mm-dd): ");
        String date = sc.nextLine();
        System.out.print("Enter slot time (HH:MM:SS): ");
        String time = sc.nextLine();

        boolean added = slotDAO.addSlot(doctorId, date, time);
        if (added) {
            System.out.println("Slot added successfully.");
        } else {
            System.out.println("Failed to add slot.");
        }
    }

    public void viewDoctorSlots(int doctorId) {
        slotDAO.viewSlotsByDoctor(doctorId);
    }

    public void viewAvailableSlots(int doctorId) {
        slotDAO.viewAvailableSlotsByDoctor(doctorId);
    }
}