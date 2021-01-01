package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository  extends CrudRepository<Patient, Long> {

}
