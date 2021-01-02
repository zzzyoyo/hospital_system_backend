package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Head_nurse;
import org.springframework.data.repository.CrudRepository;

public interface HeadNurseRepository extends CrudRepository<Head_nurse,Long> {
    Head_nurse findByUsername(String name);
}
