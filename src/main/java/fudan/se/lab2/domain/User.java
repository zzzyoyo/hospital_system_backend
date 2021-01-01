package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author LBW
 */
@Entity
@Table(name = "user")
public class User implements UserDetails {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
   //@Column(name="userId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String fullname;
    private String email;
    private String sector;
    private String country;

    //@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    //修改：user只有三个身份的可能，chair,PC member 和author一共给3个set，每个set里存储对应的会议名和id
   // private Set<Authority> authorities = new HashSet<>();
    /**
     * name:中间表的名称，若不指定，则使用默认的表名称如下所示。“表名1”+“_”+“表名2”。
     *  joinColumns属性表示，在保存关系中的表中，所保存关联关系的外键的字段。并配合@JoinColumn标记使用。
     *例如以下的映射配置，表示字段customer_id为外键关联到customer表中的id字段。
     * joinColumns={
     *             @JoinColumn(name="customer_id",referencedColumnName="id"
     * }
     *
     * inverseJoinColumns属性与joinColumns属性类似，它保存的是保存关系的另一个外键字段。
     *例如以下的映射配置，表示字段address_id为外键关联到address表中的id字段。
      inverseJoinColumns={
     *
     *          @JoinColumn(name="address_id",referencedColumnName="id")
     * }
     *
     * 提示：@JoinTable不仅能够定义一对多的关联，也可以定义多对多表的关联
     * 在这里，中间表为"user_meeting_as_chair"，中间表的”user_id"对应user表的“id”，中间表的“meeting_as_chair_id"对应meeting表的”meetingId"
     *
    @JoinTable(
            name = "user_meeting_as_chair",
            joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="meeting_as_chair_id",referencedColumnName="meetingId")}
    )
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   private Set <Meeting>  meetingAsChair = new HashSet<Meeting>();

    public Set<Meeting> getMeetingAsChair(){
        return meetingAsChair;
    }
    public void addMeetingAsChair(Meeting meeting){
        meetingAsChair.add(meeting);
    }
    public void setMeetingAsChair(Set<Meeting> meetingSet){
        meetingAsChair = meetingSet;
    }
*/
    @OneToMany(mappedBy="chair",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set <Meeting>  meetingAsChair = new HashSet<>();

    public Set<Meeting> getMeetingAsChair(){
        return meetingAsChair;
    }
    public void addMeetingAsChair(Meeting meeting){
        meetingAsChair.add(meeting);
    }
    public void setMeetingAsChair(Set<Meeting> meetingSet){
        meetingAsChair = meetingSet;
    }

    @JoinTable(
            name = "user_meeting_as_author",
            joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="meeting_as_author_id",referencedColumnName="meetingId")}
    )
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set <Meeting>  meetingAsAuthor = new HashSet<>();

    public Set<Meeting> getMeetingAsAuthor(){
        return meetingAsAuthor;
    }
    public void addMeetingAsAuthor(Meeting meeting){
        meetingAsAuthor.add(meeting);
    }public void setMeetingAsAuthor(Set<Meeting> meetingSet){
        meetingAsAuthor = meetingSet;
    }






    @JoinTable(
            name = "user_meeting_aspcmember",
            joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="meeting_aspcmember_id",referencedColumnName="meetingId")}
    )

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set <Meeting>  meetingAsPCmember = new HashSet<>();

    public Set<Meeting> getMeetingAsPCmember(){
        return meetingAsPCmember;
    }
    public void addMeetingAsPCmember(Meeting meeting){
        meetingAsPCmember.add(meeting);
    }
    public void setMeetingAsPCmember(Set<Meeting> meetingSet){
        meetingAsPCmember = meetingSet;
    }


    public User() {}
    public User(String username, String password, String fullname,String email,String sector,String country) {
        this.username = username;
        this.password= password;
        this.fullname = fullname;
        this.email = email;
        this.sector = sector;
        this.country = country;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return authorities;
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /*  public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }*/
}
