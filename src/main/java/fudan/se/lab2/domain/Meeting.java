package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "meeting")
public class Meeting {    //implements?
    //    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @Column(name="meetingId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long id;

    @Column
    private Long adminId;

    @Column(unique = true)
    private String fullname;

    @Column(unique = true)
    private String shortname;

    @Column
    private Long chairId;
    @ManyToOne (cascade=CascadeType.MERGE)
    private User chair;

    //会议建立时间
    private Date organizationTime;
    //会议地址
    private String place;
    //投稿截止时间
    private Date deadline;
    //结果放出时间
    private Date resultReleaseTime;
    //rubuttal截止时间
    private Date rebuttalDeadline;
 //  -1已拒绝
 //0待确认
 //1已同意(审核通过)
 //2已开启(可投稿)
 //3已开启审稿(审稿状态)
 //4已发布结果
 //5已举办线下会议
 //6会议结束
    private int meetingState;

    private Date startSubmissionTime;

    private int distributionStrategy;



    /**
     * 包含的论文

    @JoinTable(
            name = "meeting_paper",
           joinColumns={@JoinColumn(name="meeting_id",referencedColumnName="meetingId")},
            inverseJoinColumns={@JoinColumn(name="paper_id",referencedColumnName="paperId")}

    )*/

    @OneToMany(mappedBy="meeting",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    //@JoinTable(name = "meeting_paper", joinColumns = @JoinColumn(name = "EMP_ID"), inverseJoinColumns = @JoinColumn(name = "PHONE_ID"))
    private Set <Paper> papers = new HashSet<>();
    public Set<Paper> getPapers(){
        return papers;
    }

    public void addPaper(Paper paper){
        papers.add(paper);
    }


//cascade = CascadeType.MERGE,
    @ManyToMany(mappedBy = "meetingAsAuthor",fetch = FetchType.EAGER)
    private Set<User> authors = new HashSet<>();

    public Set<User> getMeetingAuthor(){
        return authors;
    }
    public void addMeetingAuthor(User user){
        authors.add(user);
    }
    public void setMeetingAuthors(Set<User> userSet){
        authors = userSet;
    }



    @ManyToMany(mappedBy = "meetingAsPCmember", fetch = FetchType.EAGER)
    private Set <User>  PCmembers= new HashSet<>();

    public Set<User> getMeetingPCmembers(){
        return PCmembers;
    }
    public void addMeetingPCmembers(User user){
        PCmembers.add(user);
    }
    public void setMeetingPCmembers(Set<User> userSet){
        PCmembers = userSet;
    }



    @JoinTable(
          name = "meeting_topic",
            joinColumns={@JoinColumn(name="meetingId",referencedColumnName="meetingId")},
            inverseJoinColumns={@JoinColumn(name="topicId",referencedColumnName="topicId")}
    )
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set <Topic> topicsOfMeeting= new HashSet<>();

    public Set<Topic> getTopicsOfMeeting() {
        return topicsOfMeeting;
    }

    /**
     * 注意！！！这里传入的是topic不是Sring！！！意味着必须先新建+保存topic or 已有topic 再传值
     * @param topic
     */
    public void addTopics(Topic topic){
        topicsOfMeeting.add(topic);

    }

    public void setTopics(Set<Topic> topics) {
        this.topicsOfMeeting = topics;
    }

    /**
     * 论文和meeting应该也是多对多的关系
     */

    public Meeting() {}
    public Meeting(String shortname,String fullname,Long adminId,Long chairId,User chair,int distributionStrategy){
        this.shortname = shortname;
        this.fullname = fullname;
        this.adminId = adminId;
        this.chairId = chairId;
        this.meetingState = 0;
        this.chair = chair;
        this.distributionStrategy = distributionStrategy;
    }
    public Meeting(String shortname,String fullname,Date organizationTime,String place,Date deadline,Date resultReleaseTime) {
        this.shortname = shortname;
        this.fullname = fullname;
        this.organizationTime = organizationTime;
        this.place = place;
        this.deadline = deadline;
        this.resultReleaseTime = resultReleaseTime;
//        this.adminId = adminId;
//        this.chairId = chairId;
        this.meetingState = 0;
    }
    public Meeting(String shortname,String fullname,Date organizationTime,String place,Date deadline,Date resultReleaseTime,User chair) {
        this.shortname = shortname;
        this.fullname = fullname;
        this.organizationTime = organizationTime;
        this.place = place;
        this.deadline = deadline;
        this.resultReleaseTime = resultReleaseTime;
//        this.adminId = adminId;
//        this.chairId = chairId;
        this.meetingState = 0;
        this.chair = chair;
//        this.distributionStrategy = distributionStrategy;
    }

    public Long getAdminId(){return adminId;}
    public Long getId(){
        return id;
    }


    public Long getChairId(){ return chairId; }


    public String getFullname() {
        return fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public Date getOrganizationTime() {
        return organizationTime;
    }

    public String getPlace() {
        return place;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getResultReleaseTime() {
        return resultReleaseTime;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPlace(String place) {
        this.place = place;
    }

    public int getMeetingState(){
        return meetingState;
    }

    public int getDistributionStrategy() {
        return distributionStrategy;
    }

    public void setMeetingState(int state) {
        this.meetingState = state;
    }

    public void setChair(User chair) {
        this.chair = chair;
    }


    public void setDistributionStrategy(int distributionStrategy) {
        this.distributionStrategy = distributionStrategy;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setResultReleaseTime(Date resultReleaseTime) {
        this.resultReleaseTime = resultReleaseTime;
    }

    public User getChair() {
        return chair;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setChairId(Long chairId) {
        this.chairId = chairId;
    }

    public void setStartSubmissionTime(Date startSubmissionTime) {
        this.startSubmissionTime = startSubmissionTime;
    }

    public Date getStartSubmissionTime() {
        return startSubmissionTime;
    }

    public Date getRebuttalDeadline() {
        return rebuttalDeadline;
    }

    public void setRebuttalDeadline(Date rebuttalDeadline) {
        this.rebuttalDeadline = rebuttalDeadline;
    }
}
