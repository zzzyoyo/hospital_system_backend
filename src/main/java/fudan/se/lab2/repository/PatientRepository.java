package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PatientRepository extends CrudRepository<Patient,Long> {
    Patient findByName(String name);
    Set<Patient> findByTreatmentArea(int treatmentArea);



}
