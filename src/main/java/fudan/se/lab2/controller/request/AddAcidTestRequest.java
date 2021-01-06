package fudan.se.lab2.controller.request;

import java.util.Date;

public class AddAcidTestRequest {
    private String patientName;//病人的username,
    private int condition_rating;//012,
    private int result;//阴性 = 0，阳性 =1,
    private Date date;// date类型
    public AddAcidTestRequest(){}
    public AddAcidTestRequest(String patientName,int condition_rating,int result,Date date){
        this.patientName = patientName;
        this.condition_rating = condition_rating;
        this.result = result;
        this.date = date;

    }

    public String getPatientName() {
        return patientName;
    }

    public int getResult() {
        return result;
    }

    public int getCondition_rating() {
        return condition_rating;
    }

    public Date getDate() {
        return date;
    }
}
