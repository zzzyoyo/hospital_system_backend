package fudan.se.lab2.domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @Column(name = "topicId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long topicid;

    @Column(name = "topicname",unique = true)
    private String topicname;



    @ManyToMany(mappedBy = "topicsOfMeeting", fetch = FetchType.EAGER)
    private Set <Meeting>  meetings= new HashSet<>();

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }
    public void addMeeting(Meeting meeting){
        meetings.add(meeting);
    }

    @ManyToMany(mappedBy = "topicsOfPaper", fetch = FetchType.EAGER)
    private Set <Paper>  papers= new HashSet<>();

    public Set<Paper> getPapers() {
        return papers;
    }

    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

    public void addPaper(Paper paper){
        papers.add(paper);
    }



    public Topic(){

    }

    public Topic(String topic) {
        this.topicname = topic;
    }

    public Long getTopicid() {
        return topicid;
    }

    public String getTopicname() {
        return topicname;
    }
}
