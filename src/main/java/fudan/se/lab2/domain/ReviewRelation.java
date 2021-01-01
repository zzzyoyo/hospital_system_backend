package fudan.se.lab2.domain;
import javax.persistence.*;



@Entity
@Table(name = "reviewRelation")
public class ReviewRelation {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long id;

    @Column
    private String PCmemberUsername;   //其实是username

    @Column
    private String paperTitle;

    @Column
    private String meetingFullname;

    @Column
    private int reviewStatus;//0：尚未审核 1：已一次审核

    @Column
    private int score;//-2 ->reject， -1 -> weak reject， 1 -> weak accept， 2 -> accept

    @Column
    private String confidence;//very low ,low , high ,very high

    @Column
    private String comments;//800字以内

    @Column
    private int firstModification;//0 未修改，1 已修改(-1 无法修改)

    @Column
    private int secondModification;

    public ReviewRelation(){}

    public ReviewRelation(String PCmemberUsername,String paperTitle,String meetingFullname ){
        this.meetingFullname = meetingFullname;
        this.PCmemberUsername = PCmemberUsername;
        this.paperTitle = paperTitle;
        this.reviewStatus = 0;
        this.comments = null;
        this.confidence =null;
        this.score = 0;
        this.firstModification = 0;
        this.secondModification = 0;
    }
    public ReviewRelation(String PCmemberUsername,String paperTitle,String meetingFullname ,int reviewStatus,int score,String comments,String confidence){
        this.meetingFullname = meetingFullname;
        this.PCmemberUsername = PCmemberUsername;
        this.paperTitle = paperTitle;
        this.reviewStatus = reviewStatus;
        this.comments = comments;
        this.confidence =confidence;
        this.score = score;
        this.firstModification = 0;
        this.secondModification = 0;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setPCmemberUsername(String PCmemberUsername) {
        this.PCmemberUsername = PCmemberUsername;
    }

    public String getPCmemberUsername() {
        return PCmemberUsername;
    }

    public int getFirstModification() {
        return firstModification;
    }

    public void setFirstModification(int firstModification) {
        this.firstModification = firstModification;
    }

    public int getSecondModification() {
        return secondModification;
    }


    public void setSecondModification(int secondModification) {
        this.secondModification = secondModification;
    }
}
