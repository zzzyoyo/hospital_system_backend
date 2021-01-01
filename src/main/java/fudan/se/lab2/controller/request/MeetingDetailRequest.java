package fudan.se.lab2.controller.request;

public class MeetingDetailRequest {
    private String fullname;

    public MeetingDetailRequest(){}
    public MeetingDetailRequest(String fullname ){
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }
}

