package fudan.se.lab2.controller.request;

public class RatingReviseRequest {
    private int patientID;
    private int condition_rating;

    public RatingReviseRequest(){}

    public RatingReviseRequest(int patientID, int condition_rating){
        this.condition_rating = condition_rating;
        this.patientID = patientID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getCondition_rating() {
        return condition_rating;
    }

    public void setCondition_rating(int condition_rating) {
        this.condition_rating = condition_rating;
    }
}
