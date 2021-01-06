package fudan.se.lab2.controller.request;

import java.util.Date;

public class AddDailyRecordRequest {
    private String patientName;
    private float temperature;
    private String symptom;
    private Date date;
    public AddDailyRecordRequest(){}
    public AddDailyRecordRequest(String patientName,float temperature,
                                 String symptom,Date date){
        this.patientName = patientName;
        this.temperature = temperature;
        this.symptom = symptom;
        this.date = date;
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

