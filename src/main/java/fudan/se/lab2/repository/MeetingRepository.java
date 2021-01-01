package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Meeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    Meeting findByFullname(String fullname);
    Meeting findByShortname(String shortname);
    Set<Meeting> findByChairId(Long chair);
    Set<Meeting> findByMeetingState(int state);
    Set<Meeting>findAll();
}
