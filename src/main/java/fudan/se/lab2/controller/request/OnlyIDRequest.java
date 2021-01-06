package fudan.se.lab2.controller.request;

public class OnlyIDRequest {
    private int patientID;

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public OnlyIDRequest(int patientID) {
        this.patientID = patientID;
    }

    public OnlyIDRequest() {
    }
}
