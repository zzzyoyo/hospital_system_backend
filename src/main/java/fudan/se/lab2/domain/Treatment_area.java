package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统初始化：
 * 1. 设置三个病区
 * 2. 设置三个病区的对应病床
 */

@Entity
@Table(name = "treatment_area")
public class Treatment_area {
    @Id
    @Column(name="treatment_area_Id")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long treatment_area_id;

    @OneToOne
    private Head_nurse head_nurse;



    @OneToOne
    private Doctor doctor;


    @Column(name = "type", unique = true)
    private int type; //4 轻症 2 重症 1危重症


    @OneToMany(mappedBy = "treatment_area",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Bed> beds = new HashSet<>();

    @OneToMany(mappedBy = "treatment_area",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Ward_nurse> ward_nurses = new HashSet<>();

    /**
     * 病人转入病区：
     * 1. beds.size 得出病人上限制 max
     * 2. patient.size 得出当前病人个数  patient_num
     * 3. 如果max>patient_num,判断有没有空闲的病床
     * 4. 判断有没有空闲的nurse
     * 4. 有空闲的ward_nurse_Free,在beds中寻找对应patient 为null的病床pointed_bed,关联pointed_bed,patienth和ward_nurse_Free
     * 5. 修改nurse是否free的状态
     */





   public Treatment_area(int type){
       this.type = type;
   }
   public Treatment_area(){

   }

    public Treatment_area(Head_nurse head_nurse,Doctor doctor){
        this.head_nurse = head_nurse;
        this.doctor = doctor;
    }

    public int getType() {
        return type;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }
    public void addBeds(Bed bed){
        this.beds.add(bed);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Head_nurse getHead_nurse() {
        return head_nurse;
    }

    public Long getTreatment_area_id() {
        return treatment_area_id;
    }

    public Set<Bed> getBeds() {
        return beds;
    }

    public Set<Ward_nurse> getWard_nurses() {
        return ward_nurses;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setHead_nurse(Head_nurse head_nurse) {
        this.head_nurse = head_nurse;
    }

    public void setWard_nurses(Set<Ward_nurse> ward_nurses) {
        this.ward_nurses = ward_nurses;
    }
}
