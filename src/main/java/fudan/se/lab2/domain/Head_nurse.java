package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "head_nurse")
public class Head_nurse implements  Medical_personnel{
    /**
     * todo:添加和病房的依赖
     */
    @Id
    @Column(name="headNurseId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long headNurseId;

    @Column //默认这一列叫username
    private String username;

    @Column
    private String telephone = "120";
    @Column
    private String password;

    @OneToOne
    private Treatment_area treatment_area;

    @Column
    private int identity = 1;//1 head_nurse


    public Head_nurse(String name,String password){
         this.username = name;
        this.password = password;

    }
    public Head_nurse(){}

    /*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        //护士长可以查看当前治疗区域的病人信息并支持不同条件的筛选（例如根据
        //是否满足出院条件、是否待转入其他治疗区域、病人生命状态等）
        authList.add(new SimpleGrantedAuthority("Check_All_Patient_Status"));
        //可以查看本治疗区域的病房护士信息，以及病房护士负责的病人信息
        authList.add(new SimpleGrantedAuthority("Check_All_Nurses_Status"));
        // 可以增删该区域的病房护士信息
        authList.add(new SimpleGrantedAuthority("Modify_Nurses_Status"));

        // 可以查看本治疗区域的病床信息，以及病床的病人信息（若未安排病人，则病床状态为空置）
        authList.add(new SimpleGrantedAuthority("Check_Beds_Status"));

        return authList;

    }*/

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public int getIdentity() {
        return identity;
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
