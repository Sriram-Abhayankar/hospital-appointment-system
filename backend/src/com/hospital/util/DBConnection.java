package com.hospital.util;

public class DBConnection {
    public static java.sql.Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String user = "root";
        String password = "root";
        java.sql.Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = java.sql.DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Database Connection Failed");
            e.printStackTrace();
        }
        return conn;
    }

}
