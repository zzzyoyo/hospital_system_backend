package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;
@Entity
@Table(name = "ward_nurse")
public class Ward_nurse  implements  Medical_personnel{

    /**
     * todo： 添加和病房的依赖
     */

    @Id
    @Column(name="wardNurseId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long wardNurseId;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;



    @Column //默认这一列叫username
    private String username;

    @Column
    private String password;

    @ManyToOne
    private Treatment_area treatment_area;

    @OneToMany(cascade =  CascadeType.MERGE)
    private Set<Patient> patients = new HashSet<>();

    public Ward_nurse(String name,String password){
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.username = name;
        this.password = password;
    }
    public Ward_nurse(){}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        // 每个治疗区域有多个病房护士， 负责患者的治疗及每天的信息登记
        // （包括体温、存在的症状、生命状态、核酸检测结果等） 。
        authList.add(new SimpleGrantedAuthority("Record_Patient_Status"));
        //急诊护士可以在系统中登记病人基本信息以及病情等级
        authList.add(new SimpleGrantedAuthority("Check_Patient_Status"));

        return authList;

    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return  this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setTreatment_area(Treatment_area treatment_area) {
        this.treatment_area = treatment_area;
    }

    public Treatment_area getTreatment_area() {
        return treatment_area;
    }
}

