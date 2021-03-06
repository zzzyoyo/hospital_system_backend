package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nucleic_acid_test_sheet")
public class Nucleic_acid_test_sheet implements  Comparable <Nucleic_acid_test_sheet>{
    @Id
    @Column(name="nucleic_acid_test_sheet_Id")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long nucleic_acid_test_sheet_Id;

    @Column
    private int result;//阴性 = 0，阳性 =1
    @Column
    private Date date;
    @Column
    private int conditional_rating;

    @ManyToOne(cascade=CascadeType.MERGE)
    private Patient patient;

    public Nucleic_acid_test_sheet(){}


    /**
     * 实际上我在想能布恩那个不要传入date，自动设置为当天日期
     * @param result
     * @param conditional_rating
     * @param patient
     */
    public Nucleic_acid_test_sheet(int result,int conditional_rating,Patient patient){
        this.patient = patient;
        this.result = result;
        this.conditional_rating = conditional_rating;
        this.date = new Date();

    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setConditional_rating(int conditional_rating) {
        this.conditional_rating = conditional_rating;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setNucleic_acid_test_sheet_Id(Long nucleic_acid_test_sheet_Id) {
        this.nucleic_acid_test_sheet_Id = nucleic_acid_test_sheet_Id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getDate() {
        return date;
    }

    public int getConditional_rating() {
        return conditional_rating;
    }

    public Long getNucleic_acid_test_sheet_Id() {
        return nucleic_acid_test_sheet_Id;
    }

    public int getResult() {
        return result;
    }

    @Override
    public int compareTo( Nucleic_acid_test_sheet nucleic_acid_test_sheet2 ) {

        if(this.getDate().before(nucleic_acid_test_sheet2.getDate()))return -1;
        else return 1;
    }
}
