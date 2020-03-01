# NotableHealth

## Run
Run this as Java application: 
In Intellij IDE: Edit Configurations --> NotableHealth

## APIs
1. Get list of all doctors: http://localhost:4567/doctors
2. Get a list of all appointments for a prticular doctor on a particlar day: http://localhost:4567/doctors?docID=1771829047&date=03/01/2020
3. Delete an existing appointment from doctor's calender: DELETE : http://localhost:4567/doctors?docID=1771829047&appointmentID=623981400
4. Add a new appointment to doctor's calender: PUT : http://localhost:4567/doctors?docID=1554048685&fName=Ganesh&lName=Kudva&date=02/29/2020&time=14:00&kind=FOLLOW_UP
