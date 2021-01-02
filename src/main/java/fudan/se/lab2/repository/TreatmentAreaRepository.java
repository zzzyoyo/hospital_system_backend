package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Treatment_area;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentAreaRepository extends CrudRepository<Treatment_area,Long> {
    Treatment_area findByType(int type);

    @Override
    long count();
}
