package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
@Table(name = "PCmemberToTopic")
public class PCmemberToTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long id;

    @Column
    private String meetingFullname;
    @Column
    private String username;
    @Column
    private String topicname;

    public PCmemberToTopic(){}
    public PCmemberToTopic(String meetingFullname,String username,String topicname){
        this.meetingFullname = meetingFullname;
        this.username = username;
        this.topicname = topicname;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }
}
