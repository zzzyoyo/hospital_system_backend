package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Nucleic_acid_test_sheet;
import fudan.se.lab2.domain.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface NucleicAcidTestSheetRepository extends CrudRepository<Nucleic_acid_test_sheet,Long> {
    Nucleic_acid_test_sheet findByPatientAndDate(Patient patient, Date date);
}
