package fudan.se.lab2.domain;


import javax.persistence.*;

@Entity
@Table(name = "emergency_nurse")
public class Emergency_nurse implements Medical_personnel {
     @Id
    @Column(name="emergencyNurseId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long emergencyNurseId;
    @Column //默认这一列叫username
    private String username;
    @Column
    private String telephone = "120";

    @Column
    private String password;
    @Column
    private int identity = 3;//3 emergency nurse


    @OneToOne
    private Treatment_area treatment_area;



    public Emergency_nurse(String name,String password){
        this.username = name;
        this.password = password;

    }
    public Emergency_nurse(){}

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        //急诊护士可以查看各区域病人信息并支持不同条件的筛选（例如根据治疗区域、
        // 是否在隔离区等待、 病人病情评级、病人生命状态等）。
        authList.add(new SimpleGrantedAuthority("Check_Patient_Status"));
       //急诊护士可以在系统中登记病人基本信息以及病情等级
        authList.add(new SimpleGrantedAuthority("ADD_Patient"));

        return authList;

    }*/

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

    public void setTreatment_area(Treatment_area treatment_area) {
        this.treatment_area = treatment_area;
    }

    public Treatment_area getTreatment_area() {
        return treatment_area;
    }
}
