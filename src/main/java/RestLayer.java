import model.*;

import javax.print.Doc;
import java.util.*;

import static spark.Spark.*;

public class RestLayer {
    private List<Doctor> doctorList;
    private Map<Integer, List<Appointment>> docAppointmentMap;
    public void serve() {
        final Random random = new Random();
        generateDoctorList();
        generateDoctorAppointmentList();

        put("/doctors", (request, response) -> {
            String docID = request.queryParams("docID");
            String date = request.queryParams("date");
            String time = request.queryParams("time");
            String fName = request.queryParams("fName");
            String lName = request.queryParams("lName");
            String kind = request.queryParams("kind");
            System.out.println("docID: " + docID + " date: " + date + " time: " + time);
            StringBuilder sb = new StringBuilder();

            List<Appointment> appointmentList = docAppointmentMap.get(Integer.valueOf(docID.trim()));
            if (appointmentList == null) {
                sb.append("Doctor with ID: " + docID + " doesnt exist");
            } else {

                String normalizedTime = normalizeTime(time);
                int cnt = getAppointmentCount(appointmentList, date, normalizedTime);

                if (cnt == 3) {
                    sb.append("Doctor is busy at time: " + time + " on date: " + date +"Please select a different date/time");
                } else {
                    int id = random.nextInt(Integer.MAX_VALUE/2);
                    Appointment.KIND kd = Appointment.KIND.NONE;
                    if (kind.equals("FOLLOW_UP"))
                        kd = Appointment.KIND.FOLLOW_UP;
                    else if (kind.equals("NEW_PATIENT"))
                        kd = Appointment.KIND.NEW_PATIENT;
                    Appointment ap = new Appointment(id, fName, lName, date, normalizedTime, kd);
                    docAppointmentMap.get(Integer.valueOf(docID)).add(ap);
                    sb.append("Appointment successfully booked at time: " + normalizedTime);
                }
            }

            return sb.toString();
        });

        delete("/doctors", "application/json",(request, response) -> {
            String docID = request.queryParams("docID");
            String appID = request.queryParams("appointmentID");
            StringBuilder sb = new StringBuilder();
            System.out.println("DocID: " + docID + "\n AppointmentID: " + appID);
            List<Appointment> appointmentList = docAppointmentMap.get(Integer.valueOf(docID));
            if (appointmentList == null) {
                sb.append("No appointment found for Doctor: " + docID);
            } else {
                for (int i = 0; i < appointmentList.size(); i++) {
                    if (appointmentList.get(i).getAppointmentID() == Integer.valueOf(appID)) {
                        appointmentList.remove(i);
                        sb.append("Appointment with ID: " + appID + " successfully deleted for Doctor: " + docID);
                    } else {
                        sb.append("Appointment with ID; " + appID + " not found for Doctor: " + docID);
                    }

                }
            }

            return sb.toString();
        });

        get("/doctors", "application/json",(request, response) -> {
            String docID = request.queryParams("docID");
            String date = request.queryParams("date");
            System.out.println("DocID: " + docID + "\n Date: " + date);
            StringBuilder sb = new StringBuilder();

            if (docID == null && date == null) {
                for (Doctor d : doctorList) {
                    sb.append("ID: " + d.getDoctorID() + "\nFirst Name: " + d.getFirstName() + "\nLast Name: " + d.getLastName() + "\n\n");
                }
            } else {
                List<Appointment> appointmentList = docAppointmentMap.get(Integer.valueOf(docID));
                if (appointmentList != null) {
                    for (Appointment ap : appointmentList) {
                        sb.append("Appointment ID: " + ap.getAppointmentID() + "\nPatient First Name: " + ap.getPatientFirstName()
                                + "\nPatient Last Name: " + ap.getPatientLastName() + "\nDate: " + ap.getDate()
                                + "\nAppointment Time: " + ap.getTime() + "\nPatient Kind: " + ap.getKind() + "\n\n");
                    }
                } else {
                    sb.append("No appointments found for Doctor: " + docID + " Appointment date: " + date);
                }
            }

            return sb.toString();
        });
    }

    public String normalizeTime(String time) {
        String[] timeArr = time.split(":");
        String min = "", hr = "";
        StringBuilder sb = new StringBuilder();
        if (timeArr.length >= 2) {
            hr = timeArr[0];
            min = timeArr[1];
        }
        int minutes = Integer.valueOf(min), hour = Integer.valueOf(hr);

        if (minutes > 0 && minutes < 15)
            minutes = 15;
        else if (minutes > 15 && minutes < 30)
            minutes = 30;
        else if (minutes > 30 && minutes < 45)
            minutes = 45;
        else if (minutes > 45 && minutes < 59) {
            if (hour < 23 ){
                hour++;
                minutes = 0;
            } else {
                hour = 0;
                minutes = 0;
            }
        }

        if (hour == 0) {
            sb.append("00:");
        } else {
            sb.append(hour + ":");
        }

        if (minutes == 0) {
            sb.append("00");
        } else {
            sb.append(minutes);
        }

        return sb.toString();
    }
    public int getAppointmentCount(List<Appointment> appointmentList, String date, String time) {
        int cnt = 0;
        for (Appointment a: appointmentList) {
            if (a.getTime().equals(time) && a.getDate().equals(date))
                cnt++;

            if (cnt == 3)
                break;
        }

        return cnt;
    }

    public void generateDoctorList() {
        doctorList = new ArrayList<Doctor>();
        final Random random = new Random();
        int id = 0;

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Patrick", "Mahomes"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Tom", "Brady"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Jimmy", "Garapollo"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Lamar", "Jackson"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Antonio", "Brown"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Joshua", "Adam"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Rob", "Gronk"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Richard", "Sherman"));

        id = random.nextInt(Integer.MAX_VALUE);
        doctorList.add(new Doctor(id, "Marshwan", "Lynch"));

    }

    private void generateDoctorAppointmentList() {
        docAppointmentMap = new HashMap<>();
        final Random random = new Random();
        int id = 0;

        for (int i = 0; i < doctorList.size(); i++)
            docAppointmentMap.put(doctorList.get(i).getDoctorID(), new ArrayList<>());
        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(0).getDoctorID()).add(new Appointment(id, "Stephen", "Curry", "03/01/2020", "10:00", Appointment.KIND.NEW_PATIENT));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(1).getDoctorID()).add(new Appointment(id, "Lebron", "James", "03/01/2020", "13:00", Appointment.KIND.NEW_PATIENT));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(2).getDoctorID()).add(new Appointment(id, "Andre", "James", "03/01/2020", "14:00", Appointment.KIND.FOLLOW_UP));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(3).getDoctorID()).add(new Appointment(id, "Pat", "Fearn", "03/01/2020", "14:00", Appointment.KIND.FOLLOW_UP));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(4).getDoctorID()).add(new Appointment(id, "Jerry", "James", "03/01/2020", "14:00", Appointment.KIND.FOLLOW_UP));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(5).getDoctorID()).add(new Appointment(id, "Andre", "James", "03/01/2020", "14:00", Appointment.KIND.FOLLOW_UP));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(6).getDoctorID()).add(new Appointment(id, "Jennifer", "James", "03/01/2020", "14:00", Appointment.KIND.FOLLOW_UP));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(7).getDoctorID()).add(new Appointment(id, "Andre", "Matthews", "03/01/2020", "14:00", Appointment.KIND.FOLLOW_UP));

        id = random.nextInt(Integer.MAX_VALUE/2);
        docAppointmentMap.get(doctorList.get(8).getDoctorID()).add(new Appointment(id, "Tony", "James", "03/01/2020", "14:00", Appointment.KIND.FOLLOW_UP));

    }
}
