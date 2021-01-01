package fudan.se.lab2.controller.request;

public class EssaysDataRequest {
    private String contactFullName;
    private int contactState;

    public EssaysDataRequest(){}
    public EssaysDataRequest(String contactFullName,int contactState){
        this.contactFullName = contactFullName;
        this.contactState = contactState;
    }

    public int getContactState() {
        return contactState;
    }

    public String getContactFullName() {
        return contactFullName;
    }

    public void setContactFullName(String contactFullName) {
        this.contactFullName = contactFullName;
    }

    public void setContactState(int contactState) {
        this.contactState = contactState;
    }
}
