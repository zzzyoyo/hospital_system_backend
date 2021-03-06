package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daily_state_records")
public class Daily_state_records implements Comparable<Daily_state_records>{
    @Id
    @Column(name="daily_state_records_Id")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long daily_state_records_Id;

    @Column
    private float temperature;
    @Column
    private Date date;
    @Column
    private int nucleic_acid_test_result;//阴性 = 0，阳性 =1
    @Column
    private int living_status;//0：住院 1：出院 2：死亡
    @Column
    private String symptom;//正常......


    @ManyToOne(cascade=CascadeType.MERGE)
    private Patient patient;

    public Daily_state_records (){}

    public Daily_state_records(float temperature,int nucleic_acid_test_result,int living_status,String  symptom,Patient patient){
        this.patient = null;
        this.living_status = living_status;//直接记录patient最新
        this.temperature = temperature;//nurse
        this.nucleic_acid_test_result = nucleic_acid_test_result;//直接记录patient最新
        this.date = new Date();
        this.symptom = symptom;//nurse
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSymptom() {
        return symptom;
    }

    public Date getDate() {
        return date;
    }

    public Patient getPatient() {
        return patient;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getLiving_status() {
        return living_status;
    }

    public int getNucleic_acid_test_result() {
        return nucleic_acid_test_result;
    }

    public Long getDaily_state_records_Id() {
        return daily_state_records_Id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDaily_state_records_Id(Long daily_state_records_Id) {
        this.daily_state_records_Id = daily_state_records_Id;
    }

    public void setLiving_status(int living_status) {
        this.living_status = living_status;
    }

    public void setNucleic_acid_test_result(int nucleic_acid_test_result) {
        this.nucleic_acid_test_result = nucleic_acid_test_result;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public int compareTo(Daily_state_records o) {
        if(this.getDate().before(o.getDate()))return -1;
        else return 1;

    }
}
