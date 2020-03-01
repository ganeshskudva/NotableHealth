package model;

public class Doctor {
    int doctorID;
    String firstName;
    String lastName;

    public Doctor(int doctorID, String firstName, String lastName) {
        this.doctorID = doctorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
