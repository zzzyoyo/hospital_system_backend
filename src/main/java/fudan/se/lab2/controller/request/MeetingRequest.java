package fudan.se.lab2.controller.request;


import java.util.Date;

public class MeetingRequest {

    private String chairname;
    private String fullname;
    private String shortname;
    private Date organizationTime;
    private String[] place;
    private Date deadline;
    private Date resultReleaseTime;
    private String[] topic;

    public MeetingRequest(){}
    public MeetingRequest(String chairname,String fullname,String shortname,Date organizationTime,String[] place,Date deadline,Date resultReleaseTime,String[] topic){
        this.chairname = chairname;
        this.fullname = fullname;
        this.shortname = shortname;
        this.organizationTime = organizationTime;
        this.place = place;
        this.deadline = deadline;
        this.resultReleaseTime = resultReleaseTime;
        this.topic = topic;
    }

    public String getChairname(){return chairname;}

    public String getFullname() {
        return fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public Date getOrganizationTime() {
        return organizationTime;
    }

    public String[] getPlace() {
        return place;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getResultReleaseTime() {
        return resultReleaseTime;
    }

    public String[] getTopic() {
        return topic;
    }

    public void setChairname(String chairname) {
        this.chairname = chairname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public void setOrganizationTime(Date organizationTime) {
        this.organizationTime = organizationTime;
    }

    public void setPlace(String[] place) {
        this.place = place;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setResultReleaseTime(Date resultReleaseTime) {
        this.resultReleaseTime = resultReleaseTime;
    }

    public void setTopic(String[] topic) {
        this.topic = topic;
    }
}
