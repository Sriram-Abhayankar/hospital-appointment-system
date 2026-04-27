package com.hospital.dao;

import com.hospital.model.Doctor;
import com.hospital.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {

    public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors(name, specialization, phone, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getSpecialization());
            pstmt.setString(3, doctor.getPhone());
            pstmt.setString(4, doctor.getEmail());
            pstmt.setString(5, doctor.getPassword());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Failed to add doctor.");
            e.printStackTrace();
            return false;
        }
    }

    public Doctor loginDoctor(String email, String password) {
        String sql = "SELECT doctor_id, name, specialization, phone, email, password FROM doctors WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setDoctorId(rs.getInt("doctor_id"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSpecialization(rs.getString("specialization"));
                    doctor.setPhone(rs.getString("phone"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setPassword(rs.getString("password"));
                    return doctor;
                }
            }
        } catch (SQLException e) {
            System.out.println("Doctor login failed.");
            e.printStackTrace();
        }
        return null;
    }

    public void viewAllDoctors() {
        String sql = "SELECT doctor_id, name, specialization, phone, email FROM doctors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println(String.format("%-10s %-20s %-20s %-15s %-25s", "ID", "Name", "Specialization", "Phone", "Email"));
            System.out.println("-------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                System.out.println(String.format("%-10d %-20s %-20s %-15s %-25s", id, name, specialization, phone, email));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve doctors.");
            e.printStackTrace();
        }
    }
} 