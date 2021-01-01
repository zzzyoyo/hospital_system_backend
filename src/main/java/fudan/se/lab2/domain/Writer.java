package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Writer {
    @Id
    @Column(name="writerId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long writerid;

    @Column(unique = true)
    private String writername;

    @Column(unique = true)
    private String email;
    private String sector;
    private String country;


    @ManyToMany(mappedBy = "writersOfPaper",fetch = FetchType.EAGER)
    private Set<Paper> papers = new HashSet<>();

    public Set<Paper> getPapers() {
        return papers;
    }

    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }
    public void addPapers(Paper paper){
        this.papers.add(paper);
    }

    public Writer(){

    }
    public Writer(String writerName, String sector, String country, String email){
        this.email = email;
        this.sector = sector;
        this.country = country;
        this.writername = writerName;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }

    public Long getWriterid() {
        return writerid;
    }

    public String getEmail() {
        return email;
    }

    public String getWritername() {
        return writername;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setWriterid(Long writerid) {
        this.writerid = writerid;
    }

    public void setWritername(String writername) {
        this.writername = writername;
    }
}
