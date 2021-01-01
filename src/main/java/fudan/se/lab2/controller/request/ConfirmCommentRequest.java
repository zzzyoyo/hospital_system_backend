package fudan.se.lab2.controller.request;


public class ConfirmCommentRequest {
    private String meetingFullname;
    private String authorName ;
    private String essayTitle;
    private String speaker;
    private String pcMemberUsername;

    public ConfirmCommentRequest(){}
    public ConfirmCommentRequest(String meetingFullname,
                                 String authorName,
                                 String essayTitle,String speaker,
                                 String pcMemberUsername){
        this.meetingFullname =meetingFullname;
        this.authorName = authorName;
        this.essayTitle = essayTitle;
        this.speaker = speaker;
        this.pcMemberUsername = pcMemberUsername;
    }

    public String getSpeaker() {
        return speaker;
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

    public String getPcMemberUsername() {
        return pcMemberUsername;
    }
}
