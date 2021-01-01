package fudan.se.lab2.controller.request;

public class JudgeRoleInMeetingRequest {
    private String fullname;
    private String username;
    public JudgeRoleInMeetingRequest(){}
    public JudgeRoleInMeetingRequest(String fullname,String username){
        this.fullname = fullname;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
