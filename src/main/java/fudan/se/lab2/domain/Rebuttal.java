package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
@Table(name = "rebuttal")
public class Rebuttal {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
    private Long id;

    @Column
    private String title;

    @Column
    private String rebuttalRequest;
    public Rebuttal(){}

    public Rebuttal(String title,String rebuttalRequest){
        this.title = title;
        this.rebuttalRequest = rebuttalRequest;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRebuttalRequest(String rebuttalRequest) {
        this.rebuttalRequest = rebuttalRequest;
    }

    public String getRebuttalRequest() {
        return rebuttalRequest;
    }
}

