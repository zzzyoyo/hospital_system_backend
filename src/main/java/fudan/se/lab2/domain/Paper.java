package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Paper {
    /*
    authorname用fullname！！！！
     */
    @Id
    @Column(name ="paperId")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long paperId;
    @Column
    private String authorname; //fullname
    @Column(unique = true)
    private String title;
    @Column
    private String summary;
    @Column
    private String path;
//    @Column
//    private int restriction;  //paper的writer中是pcmember的人数
    @Column
    private int rebuttal;   //已录用 -1    未通过待提交rebuttal 0      已提交 1


    @ManyToOne (cascade=CascadeType.MERGE)
    private Meeting meeting;

    /**
     * Lab4 新增
     */


    @JoinTable(
            name = "paper_writer",
            joinColumns={@JoinColumn(name="paperId",referencedColumnName="paperId")},
            inverseJoinColumns={@JoinColumn(name="writerId",referencedColumnName="writerId")}
    )
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set <Writer> writersOfPaper= new HashSet<>();

    public Set<Writer> getWritersOfPaper() {
        return writersOfPaper;
    }

    public void setWritersOfPaper(Set<Writer> writersOfPaper) {
        this.writersOfPaper = writersOfPaper;
    }
    public void addWritersOfPaper(Writer writer) {
        this.writersOfPaper .add(writer);
    }

    @JoinTable(
            name = "paper_topic",
            joinColumns={@JoinColumn(name="paperId",referencedColumnName="paperId")},
            inverseJoinColumns={@JoinColumn(name="topicId",referencedColumnName="topicId")}
    )
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set <Topic> topicsOfPaper= new HashSet<>();

    public Set<Topic> getTopicsOfPaper() {
        return topicsOfPaper;
    }

    public void setTopicsOfPaper(Set<Topic> topicsOfPaper) {
        this.topicsOfPaper = topicsOfPaper;
    }

    /**
     * 注意！！！这里传入的是topic不是Sring！！！意味着必须先新建+保存topic or 已有topic 再传值
     * @param topic
     */
    public void addTopics(Topic topic){
        topicsOfPaper.add(topic);

    }



    public Paper(){
    }
    public Paper(String authorname, String title, String summary ,Meeting meeting){
        this.authorname = authorname;
         this.title = title;
         this.summary = summary;
         this.meeting = meeting;
         this.rebuttal = -2;
        // this.meetingFullname = meetingFullname;
    }

    public Long getPaperId() {
        return paperId;
    }

    public String getAuthorname(){
        return authorname;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

   public void setMeeting(Meeting meeting){
        this.meeting = meeting;
   }

    public Meeting getMeeting() {
        return meeting;
    }

//    public int getRestriction() {
//        return restriction;
//    }
//
//    public void setRestriction(int restriction) {
//        this.restriction = restriction;
//    }

    public int getRebuttal() {
        return rebuttal;
    }

    public void setRebuttal(int rebuttal) {
        this.rebuttal = rebuttal;
    }
}
