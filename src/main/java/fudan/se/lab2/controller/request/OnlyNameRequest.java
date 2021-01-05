package fudan.se.lab2.controller.request;

public class OnlyNameRequest {
    String username;
    public OnlyNameRequest(){}
    public OnlyNameRequest(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
