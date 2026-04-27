package com.hospital.dao;

import com.hospital.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SlotDAO {

    /**
     * Adds a new slot for a doctor.
     * 
     * @param doctorId The doctor's ID
     * @param date     The slot date (format: YYYY-MM-DD)
     * @param time     The slot time (e.g., "09:30")
     * @return         True if insert is successful, false otherwise
     */
    public boolean addSlot(int doctorId, String date, String time) {
        String sql = "INSERT INTO availability_slots (doctor_id, slot_date, slot_time, is_booked) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, doctorId);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            pstmt.setBoolean(4, false);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Failed to add slot.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Displays all slots for a given doctor, showing slot_id, slot_date, slot_time, is_booked.
     * 
     * @param doctorId The doctor's ID
     */
    public void viewSlotsByDoctor(int doctorId) {
        String sql = "SELECT slot_id, slot_date, slot_time, is_booked FROM availability_slots WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println(String.format("%-8s %-12s %-8s %-10s", "SlotID", "Date", "Time", "Booked"));
                System.out.println("--------------------------------------------");
                while (rs.next()) {
                    int slotId = rs.getInt("slot_id");
                    String slotDate = rs.getString("slot_date");
                    String slotTime = rs.getString("slot_time");
                    boolean isBooked = rs.getBoolean("is_booked");
                    System.out.println(String.format("%-8d %-12s %-8s %-10s", slotId, slotDate, slotTime, isBooked));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve slots.");
            e.printStackTrace();
        }
    }

    /**
     * Displays available (not booked) slots for a given doctor.
     * 
     * @param doctorId The doctor's ID
     */
    public void viewAvailableSlotsByDoctor(int doctorId) {
        String sql = "SELECT slot_id, slot_date, slot_time, is_booked FROM availability_slots WHERE doctor_id = ? AND is_booked = false";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println(String.format("%-8s %-12s %-8s %-10s", "SlotID", "Date", "Time", "Booked"));
                System.out.println("--------------------------------------------");
                while (rs.next()) {
                    int slotId = rs.getInt("slot_id");
                    String slotDate = rs.getString("slot_date");
                    String slotTime = rs.getString("slot_time");
                    boolean isBooked = rs.getBoolean("is_booked");
                    System.out.println(String.format("%-8d %-12s %-8s %-10s", slotId, slotDate, slotTime, isBooked));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve available slots.");
            e.printStackTrace();
        }
    }
}
