package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Daily_state_records;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRecordRepository extends CrudRepository<Daily_state_records, Long> {
    Daily_state_records findByPatient(String patient);
}



