package fudan.se.lab2.domain;
import javax.persistence.*;


@Entity
@Table(name = "bed")//表格名称
public class Bed {
    @Id
    @Column(name="bedId")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long bedId;

    @ManyToOne(cascade=CascadeType.MERGE)
    private Treatment_area treatment_area;

    @OneToOne
    private Patient patient;

    public Bed(){}
    public Bed(Treatment_area treatment_area){
        this.patient = null;
        this.treatment_area = treatment_area;
    }

    public Long getBedId() {
        return bedId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Treatment_area getTreatment_area() {
        return treatment_area;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setTreatment_area(Treatment_area treatment_area) {
        this.treatment_area = treatment_area;
    }
}
