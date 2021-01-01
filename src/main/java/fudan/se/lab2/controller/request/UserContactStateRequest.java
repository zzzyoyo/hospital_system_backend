package fudan.se.lab2.controller.request;

public class UserContactStateRequest {
    private String username;

    public UserContactStateRequest(){}
    public UserContactStateRequest(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
