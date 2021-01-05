package fudan.se.lab2.controller.request;

import javax.persistence.Column;

public class AddPatientRequest {
    private String name;
    private int condition_rating;//0：轻症 1： 重症 2：危重症

    private int living_status;//3：住院 4：出院 5：死亡

    public AddPatientRequest(){}
    public AddPatientRequest(String name,int living_status,int condition_rating){
        this.living_status = living_status;
        this.name = name;
        this.condition_rating = condition_rating;
    }

    public String getName() {
        return name;
    }

    public int getCondition_rating() {
        return condition_rating;
    }

    public int getLiving_status() {
        return living_status;
    }
}
