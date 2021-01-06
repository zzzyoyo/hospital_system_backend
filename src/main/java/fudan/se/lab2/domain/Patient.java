package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patient")
public class Patient {
    /**
     * todo:思考：病人要和病区挂钩嘛？是没有床号就默认在隔离区嘛？
     */
    @Id
    @Column(name="patientId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long id;

    @Column //默认这一列叫name
    private String name;

    @Column
    private int treatmentArea = 0;

    @Column
    private int condition_rating;//0：轻症 1： 重症 2：危重症
    @Column
    private int living_status;//0：住院 1：出院 2：死亡
    @OneToOne
    private Bed bed;

    @OneToMany(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
    private Set<Nucleic_acid_test_sheet> nucleic_acid_test_sheets = new HashSet<>();

    @OneToMany(cascade =  CascadeType.MERGE,fetch=FetchType.EAGER)
    private Set<Daily_state_records> daily_state_records = new HashSet<>();

    @ManyToOne
    private Ward_nurse nurse;



    public Patient(){}
    public Patient(String name, int condition_rating, int living_status){
        this.name = name;
        this.condition_rating = condition_rating;
        this.living_status = living_status;
        this.bed = null;

    }

    public void add_Daily_state_records(Daily_state_records daily_state_record){
        this.daily_state_records.add(daily_state_record);
    }
    public void add_Nucleic_acid_test_sheet(Nucleic_acid_test_sheet nucleic_acid_test_sheet){
        this.nucleic_acid_test_sheets.add(nucleic_acid_test_sheet);
    }
    public void setNucleic_acid_test_sheets(Set<Nucleic_acid_test_sheet> nucleic_acid_test_sheets) {
        this.nucleic_acid_test_sheets = nucleic_acid_test_sheets;
    }

    public void setDaily_state_records(Set<Daily_state_records> daily_state_records) {
        this.daily_state_records = daily_state_records;
    }

    public void setCondition_rating(int condition_rating) {
        this.condition_rating = condition_rating;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public void setLiving_status(int living_status) {
        this.living_status = living_status;
    }

    public int getLiving_status() {
        return living_status;
    }

    public String getName() {
        return name;
    }

    public Bed getBed() {
        return bed;
    }

    public int getCondition_rating() {
        return condition_rating;
    }

    public Long getId() {
        return id;
    }

    public int getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(int treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    public Set<Daily_state_records> getDaily_state_records() {
        return daily_state_records;
    }

    public Set<Nucleic_acid_test_sheet> getNucleic_acid_test_sheets() {
        return nucleic_acid_test_sheets;
    }

    public Ward_nurse getNurse() {
        return nurse;
    }

    public void setNurse(Ward_nurse nurse) {
        this.nurse = nurse;
    }
}
