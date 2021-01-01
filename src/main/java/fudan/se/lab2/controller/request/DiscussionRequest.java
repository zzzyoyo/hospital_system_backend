package fudan.se.lab2.controller.request;

public class DiscussionRequest {
    String meetingFullname;
    String authorName;
    String essayTitle;
    public DiscussionRequest(){}
    public DiscussionRequest(String meetingFullname,String authorName,String essayTitle){
        this.authorName = authorName;
        this.essayTitle = essayTitle;
        this.meetingFullname = meetingFullname;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getEssayTitle() {
        return essayTitle;
    }
}
