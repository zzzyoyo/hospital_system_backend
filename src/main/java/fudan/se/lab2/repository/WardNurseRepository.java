package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Ward_nurse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface WardNurseRepository extends CrudRepository<Ward_nurse,Long> {
    Ward_nurse findByUsername(String username);
    @Query("SELECT n FROM Ward_nurse n")
    Set<Ward_nurse> findAllWardNurses();
}
