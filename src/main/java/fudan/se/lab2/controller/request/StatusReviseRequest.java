package fudan.se.lab2.controller.request;

public class StatusReviseRequest {
    int patientID;
    int living_status;

    public StatusReviseRequest(){}

    public StatusReviseRequest(int patientID, int living_status) {
        this.patientID = patientID;
        this.living_status = living_status;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getLiving_status() {
        return living_status;
    }

    public void setLiving_status(int living_status) {
        this.living_status = living_status;
    }
}
