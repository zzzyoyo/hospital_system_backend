package fudan.se.lab2.controller.request;

import javax.persistence.Column;
import java.util.Date;

public class AddPatientRequest {
    private String name;
    private int condition_rating;//0：轻症 1： 重症 2：危重症
    private String result;
    private Date date;

    public AddPatientRequest(){}
    public AddPatientRequest(String name,int condition_rating,String result,Date date){
        this.result = result;
        this.name = name;
        this.condition_rating = condition_rating;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public int getCondition_rating() {
        return condition_rating;
    }


}
