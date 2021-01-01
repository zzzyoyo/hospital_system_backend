package fudan.se.lab2.controller.request;

public class UserEssayStateRequest {
    private String contactName;
    private String username;
    public UserEssayStateRequest(){
    }
    public UserEssayStateRequest(String username,String contactName){
        this.contactName = contactName;
        this.username = username;

    }

    public String getContactName() {
        return contactName;
    }

    public String getUsername() {
        return username;
    }
}
