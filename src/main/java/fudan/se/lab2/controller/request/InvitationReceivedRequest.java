package fudan.se.lab2.controller.request;

public class InvitationReceivedRequest {
    private String username;

    public InvitationReceivedRequest(){}
    public InvitationReceivedRequest(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
