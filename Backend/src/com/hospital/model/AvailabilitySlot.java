package com.hospital.model;

public class AvailabilitySlot {
    private int slotId;
    private int doctorId;
    private String slotDate;
    private String slotTime;
    private boolean isBooked;

    // Default constructor
    public AvailabilitySlot() {}

    // Parameterized constructor (without slotId)
    public AvailabilitySlot(int doctorId, String slotDate, String slotTime, boolean isBooked) {
        this.doctorId = doctorId;
        this.slotDate = slotDate;
        this.slotTime = slotTime;
        this.isBooked = isBooked;
    }

    // Getters and setters
    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(String slotDate) {
        this.slotDate = slotDate;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
}
    
