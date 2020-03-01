package model;

public class Appointment {
    int appointmentID;
    String patientFirstName;
    String patientLastName;
    String date;
    String time; //24 hr format
    KIND kind;

    public enum KIND {
        FOLLOW_UP,
        NEW_PATIENT,
        NONE
    }

    public Appointment(int appointmentID, String patientFirstName, String patientLastName, String date, String time, KIND kind) {
        this.appointmentID = appointmentID;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.date = date;
        this.time = time;
        this.kind = kind;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public String getDate() {
        return date;
    }

    public KIND getKind() {
        return kind;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setKind(KIND kind) {
        this.kind = kind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
