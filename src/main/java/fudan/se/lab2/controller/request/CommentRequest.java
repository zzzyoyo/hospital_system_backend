package fudan.se.lab2.controller.request;


/**
 *   PCmemberFullName:this.$store.state.userDetails,
 *   paperTitle:'文章标题',
 *   meetingFullname:'会议主全名',
 *   reviewStatus:1,
 *   Score:this.commentForm.score,
 *   Confidence:this.commentForm.confidence,
 *   Comments:this.commentForm.comment,
 */
public class CommentRequest {
    private String pcmemberFullName;   //其实是username
    private String paperTitle;
    private String meetingFullname;
    private int reviewStatus;
    private String[] score;
    private String[] confidence;
    private String comments;

    public CommentRequest(){}
    public CommentRequest(String pcmemberFullName,String paperTitle,String meetingFullname,int reviewStatus,String[] score,String[] confidence,String comments){
        this.pcmemberFullName = pcmemberFullName;
        this.paperTitle = paperTitle;
        this.meetingFullname = meetingFullname;
        this.score = score;
        this.confidence = confidence;
        this.comments = comments;
        this.reviewStatus = reviewStatus;
    }

    public String getPcmemberFullName() {
        return pcmemberFullName;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public String[] getScore() {
        return score;
    }

    public String[] getConfidence() {
        return confidence;
    }

    public String getComments() {
        return comments;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public void setConfidence(String[] confidence) {
        this.confidence = confidence;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public void setPcmemberFullName(String pcmemberFullName) {
        this.pcmemberFullName = pcmemberFullName;
    }

    public void setScore(String[] score) {
        this.score = score;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}