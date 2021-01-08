package fudan.se.lab2.controller.request;

import java.util.Date;

public class AddDailyRecordRequest {
    private String patientName;
    private float temperature;
    private String symptom;
    private Date date;
    private int status;
    public AddDailyRecordRequest(){}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AddDailyRecordRequest(String patientName, float temperature,
                                 String symptom, Date date, int status){
        this.patientName = patientName;
        this.temperature = temperature;
        this.symptom = symptom;
        this.date = date;
        this.status =  status;
    }

    public String getPatientName() {
        return patientName;
    }

    public Date getDate() {
        return date;
    }

    public String getSymptom() {
        return symptom;
    }

    public float getTemperature() {
        return temperature;
    }
}

