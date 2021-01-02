package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Emergency_nurse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyNurseRepository extends CrudRepository<Emergency_nurse,Long> {
  Emergency_nurse findByUsername(String name);
}
