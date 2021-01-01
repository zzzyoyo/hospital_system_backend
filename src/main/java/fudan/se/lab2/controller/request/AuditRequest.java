package fudan.se.lab2.controller.request;


public class AuditRequest {
    private String username;
    private String contactName;

    public AuditRequest(){}
    public AuditRequest(String username,String contactName){
        this.username = username;
        this.contactName = contactName;
    }
    public String getUsername(){
        return username;
    }
    public String getContactName(){
        return contactName;
    }

}
