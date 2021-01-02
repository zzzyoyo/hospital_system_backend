package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor,Long> {
    Doctor findByUsername(String username);

}
