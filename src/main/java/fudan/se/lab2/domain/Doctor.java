package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor implements Medical_personnel {
    /**
     * todo:添加和病房的依赖
     */
    @Id
    @Column(name="doctorId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long doctorId;

    @Column //默认这一列叫name
    private String username;

    @Column
    private String password;

    @Column
    private String telephone;

    @Column
    private String email;


    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        //主治医生可以查看当前治疗区域的病人信息并支持不同条件的筛选（例如
        //根据是否满足出院条件、是否待转入其他治疗区域、病人生命状态等）
        authList.add(new SimpleGrantedAuthority("Check_All_Patient_Status"));
        //可以查看当前治疗区域的护士长及病房护士信息，以及病房护士负责的病人；
        authList.add(new SimpleGrantedAuthority("Check_HeadNurse_Status"));
        authList.add(new SimpleGrantedAuthority("Check_Nurses_Status"));
        //可以修改病人的病情评级； 可以修改病人的生命状态；
        authList.add(new SimpleGrantedAuthority("Modify_Patient_Status"));

        // 可以为病人进行核酸检测（添加新的检测单）；
        authList.add(new SimpleGrantedAuthority("Add_test_sheet"));
        // 轻症治疗区域的主治医生可以根据病人情况决定病人是否可以康复出院。
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



    public Treatment_area getTreatment_area() {
        return treatment_area;
    }

    public void setTreatment_area(Treatment_area treatment_area) {
        this.treatment_area = treatment_area;
    }

    @OneToOne
    private Treatment_area treatment_area;

    public Doctor(String name, String password, Treatment_area treatment_area) {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.username = name;
        this.password = password;
    }

    public Doctor(){}


}
