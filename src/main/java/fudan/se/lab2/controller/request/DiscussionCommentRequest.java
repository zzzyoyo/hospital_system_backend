package fudan.se.lab2.controller.request;

public class DiscussionCommentRequest {
    private String meetingFullname;
     private String authorName ;
    private String essayTitle;
    private String speaker;
    private String content;
    public DiscussionCommentRequest(){};

    public DiscussionCommentRequest(String meetingFullname,String authorName,String essayTitle,String speaker,String content ){
        this.authorName = authorName;
        this.essayTitle = essayTitle;
        this.speaker = speaker;
        this.content = content;
        this.meetingFullname = meetingFullname;
    }

    public String getEssayTitle() {
        return essayTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getContent() {
        return content;
    }
}
