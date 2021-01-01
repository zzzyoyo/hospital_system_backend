package fudan.se.lab2.repository;
import fudan.se.lab2.domain.PCmemberToTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PCmemberToTopicRepository extends CrudRepository<PCmemberToTopic, Long> {

    Set<PCmemberToTopic> findByMeetingFullnameAndAndUsername(String meetingFullname, String username);
    Set<PCmemberToTopic> findByMeetingFullnameAndAndTopicname(String meetingFullname, String topicname);
    PCmemberToTopic findByMeetingFullnameAndTopicnameAndAndUsername(String meetingFullname,String topicname,String username);

}
