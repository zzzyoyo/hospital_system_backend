package fudan.se.lab2.controller.request;

public class PersonalInformationRequest {
    private String username;

    public PersonalInformationRequest(){}
    public PersonalInformationRequest(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
