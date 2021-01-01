package fudan.se.lab2.controller.request;

import java.util.Date;

public class ContactInformationRequest {
    private String contactFullName;
    private Date rebuttalDeadline;

    public ContactInformationRequest(){}
    public ContactInformationRequest(String contactFullName){
        this.contactFullName = contactFullName;
    }
    public ContactInformationRequest(String contactFullName,Date rebuttalDeadline){
        this.contactFullName = contactFullName;
        this.rebuttalDeadline = rebuttalDeadline;
    }

    public String getContactFullName() {
        return contactFullName;
    }

    public void setContactFullName(String contactFullName) {
        this.contactFullName = contactFullName;
    }

    public Date getRebuttalDeadline() {
        return rebuttalDeadline;
    }

    public void setRebuttalDeadline(Date rebuttalDeadline) {
        this.rebuttalDeadline = rebuttalDeadline;
    }
}
