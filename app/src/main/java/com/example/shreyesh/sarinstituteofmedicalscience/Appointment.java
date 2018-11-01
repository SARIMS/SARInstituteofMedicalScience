package com.example.shreyesh.sarinstituteofmedicalscience;

public class Appointment {

    private String doctorName, appointmentTime, appointmentDate, id;

    public Appointment() {
    }

    public Appointment(String doctorName, String appointmentTime, String appointmentDate) {
        this.doctorName = doctorName;
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
    }

    public Appointment(String doctorName, String appointmentTime, String appointmentDate, String id) {
        this.doctorName = doctorName;
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
