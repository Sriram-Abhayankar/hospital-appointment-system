package com.hospital.dao;
import com.hospital.util.DBConnection;

import java.sql.*;

public class AppointmentDAO {
    public boolean updateAppointmentStatus(int appointmentId, int doctorId, String newStatus) {
        // Only allow statuses: Approved, Completed, Rejected
        if (!("Approved".equalsIgnoreCase(newStatus) ||
              "Completed".equalsIgnoreCase(newStatus) ||
              "Rejected".equalsIgnoreCase(newStatus))) {
            System.out.println("Invalid status. Allowed: Approved, Completed, Rejected.");
            return false;
        }

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();

            // Verify the appointment belongs to the doctor
            String checkSql = "SELECT appointment_id FROM appointments WHERE appointment_id = ? AND doctor_id = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, appointmentId);
            checkStmt.setInt(2, doctorId);
            rs = checkStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Appointment does not exist or does not belong to the specified doctor.");
                return false;
            }

            // Update appointment status
            String updateSql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newStatus);
            updateStmt.setInt(2, appointmentId);
            int updatedRows = updateStmt.executeUpdate();

            if (updatedRows > 0) {
                System.out.println("Appointment status updated to " + newStatus + ".");
                return true;
            } else {
                System.out.println("Failed to update appointment status.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating appointment status.");
            e.printStackTrace();
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (checkStmt != null) checkStmt.close(); } catch (SQLException ignored) {}
            try { if (updateStmt != null) updateStmt.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
    public void viewDoctorAppointments(int doctorId) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        conn = DBConnection.getConnection();
        String sql = "SELECT appointment_id, patient_id, slot_id, status, booked_at FROM appointments WHERE doctor_id = ? AND status <> 'Cancelled'";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, doctorId);
        rs = stmt.executeQuery();

        System.out.println("Appointments for Doctor ID: " + doctorId);
        System.out.printf("%-15s %-12s %-10s %-12s %-25s%n", "Appointment ID", "Patient ID", "Slot ID", "Status", "Booked At");
        while (rs.next()) {
            int appointmentId = rs.getInt("appointment_id");
            int patientId = rs.getInt("patient_id");
            int slotId = rs.getInt("slot_id");
            String status = rs.getString("status");
            Timestamp bookedAt = rs.getTimestamp("booked_at");

            System.out.printf("%-15d %-12d %-10d %-12s %-25s%n",
                    appointmentId, patientId, slotId, status, bookedAt != null ? bookedAt.toString() : "");
        }
    } catch (SQLException e) {
        System.out.println("Failed to retrieve doctor's appointments.");
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
    }
}

public boolean bookAppointment(int patientId, int doctorId, int slotId) {
    Connection conn = null;
    PreparedStatement checkStmt = null;
    PreparedStatement insertStmt = null;
    PreparedStatement updateSlotStmt = null;
    ResultSet rs = null;
    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false); // Begin transaction

        // 1. Check if slot is not already booked
        String checkSql = "SELECT is_booked FROM availability_slots WHERE slot_id = ?";
        checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, slotId);
        rs = checkStmt.executeQuery();
        if (rs.next()) {
            boolean isBooked = rs.getBoolean("is_booked");
            if (isBooked) {
                System.out.println("Slot already booked!");
                conn.rollback();
                return false;
            }
        } else {
            System.out.println("Slot does not exist.");
            conn.rollback();
            return false;
        }

        // 2. Insert into appointments
        String insertSql = "INSERT INTO appointments (patient_id, doctor_id, slot_id, status, booked_at) VALUES (?, ?, ?, ?, ?)";
        insertStmt = conn.prepareStatement(insertSql);
        insertStmt.setInt(1, patientId);
        insertStmt.setInt(2, doctorId);
        insertStmt.setInt(3, slotId);
        insertStmt.setString(4, "Pending");

        Timestamp now = new Timestamp(System.currentTimeMillis());
        insertStmt.setTimestamp(5, now);

        int rowsInserted = insertStmt.executeUpdate();
        if (rowsInserted == 0) {
            System.out.println("Could not book appointment.");
            conn.rollback();
            return false;
        }

        // 3. Update slot as booked
        String updateSlotSql = "UPDATE availability_slots SET is_booked = true WHERE slot_id = ?";
        updateSlotStmt = conn.prepareStatement(updateSlotSql);
        updateSlotStmt.setInt(1, slotId);
        int slotUpdated = updateSlotStmt.executeUpdate();
        if (slotUpdated == 0) {
            System.out.println("Could not update slot.");
            conn.rollback();
            return false;
        }

        conn.commit();
        System.out.println("Appointment booked successfully.");
        return true;
    } catch (SQLException e) {
        System.out.println("Failed to book appointment.");
        e.printStackTrace();
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (checkStmt != null) checkStmt.close(); } catch (SQLException ignored) {}
        try { if (insertStmt != null) insertStmt.close(); } catch (SQLException ignored) {}
        try { if (updateSlotStmt != null) updateSlotStmt.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
    }
}

public void viewPatientAppointments(int patientId) {
    String sql = "SELECT appointment_id, doctor_id, slot_id, status, booked_at FROM appointments WHERE patient_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, patientId);
        try (ResultSet rs = pstmt.executeQuery()) {
            System.out.println(String.format("%-15s %-10s %-10s %-10s %-25s", "AppointmentID", "DoctorID", "SlotID", "Status", "BookedAt"));
            System.out.println("--------------------------------------------------------------------------");
            boolean found = false;
            while (rs.next()) {
                int aid = rs.getInt("appointment_id");
                int did = rs.getInt("doctor_id");
                int sid = rs.getInt("slot_id");
                String status = rs.getString("status");
                Timestamp bookedAt = rs.getTimestamp("booked_at");

                System.out.println(String.format("%-15d %-10d %-10d %-10s %-25s", aid, did, sid, status, bookedAt));
                found = true;
            }
            if (!found) {
                System.out.println("No appointments found for this patient.");
            }
        }
    } catch (SQLException e) {
        System.out.println("Failed to retrieve patient appointments.");
        e.printStackTrace();
    }
}

public boolean cancelAppointment(int appointmentId, int patientId) {
    Connection conn = null;
    PreparedStatement checkStmt = null;
    PreparedStatement updateApptStmt = null;
    PreparedStatement updateSlotStmt = null;
    ResultSet rs = null;
    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        // Get slot_id for this appointment (and check ownership)
        String checkSql = "SELECT slot_id, status FROM appointments WHERE appointment_id = ? AND patient_id = ?";
        checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, appointmentId);
        checkStmt.setInt(2, patientId);
        rs = checkStmt.executeQuery();
        if (rs.next()) {
            String status = rs.getString("status");
            if ("Cancelled".equalsIgnoreCase(status)) {
                System.out.println("Appointment is already cancelled.");
                conn.rollback();
                return false;
            }
            int slotId = rs.getInt("slot_id");

            // Update appointment status
            String updateApptSql = "UPDATE appointments SET status = 'Cancelled' WHERE appointment_id = ?";
            updateApptStmt = conn.prepareStatement(updateApptSql);
            updateApptStmt.setInt(1, appointmentId);
            int updatedRows = updateApptStmt.executeUpdate();
            if (updatedRows == 0) {
                System.out.println("Failed to update appointment status.");
                conn.rollback();
                return false;
            }

            // Update slot as available
            String updateSlotSql = "UPDATE availability_slots SET is_booked = false WHERE slot_id = ?";
            updateSlotStmt = conn.prepareStatement(updateSlotSql);
            updateSlotStmt.setInt(1, slotId);
            int updatedSlotRows = updateSlotStmt.executeUpdate();
            if (updatedSlotRows == 0) {
                System.out.println("Failed to update slot as available.");
                conn.rollback();
                return false;
            }

            conn.commit();
            System.out.println("Appointment cancelled successfully.");
            return true;
        } else {
            System.out.println("Appointment not found or does not belong to this patient.");
            conn.rollback();
            return false;
        }
    } catch (SQLException e) {
        System.out.println("Failed to cancel appointment.");
        e.printStackTrace();
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (checkStmt != null) checkStmt.close(); } catch (SQLException ignored) {}
        try { if (updateApptStmt != null) updateApptStmt.close(); } catch (SQLException ignored) {}
        try { if (updateSlotStmt != null) updateSlotStmt.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
    }
}
}