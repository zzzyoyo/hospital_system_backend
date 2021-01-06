package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

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
    private int identity = 0;//0 doctor
    @Override
    public int getIdentity() {
        return identity;
    }


    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Doctor(String name, String password) {

        this.username = name;
        this.password = password;
        this.telephone = "120";
    }

    public Doctor(){}


}
