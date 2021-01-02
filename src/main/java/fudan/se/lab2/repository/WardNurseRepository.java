package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Ward_nurse;
import org.springframework.data.repository.CrudRepository;
public interface WardNurseRepository extends CrudRepository<Ward_nurse,Long> {
    Ward_nurse findByUsername(String username);
}
