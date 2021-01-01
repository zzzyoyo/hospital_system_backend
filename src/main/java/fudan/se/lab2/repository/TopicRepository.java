package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TopicRepository extends CrudRepository<Topic,Long> {
    Topic findByTopicid(Long topic);
    Topic findByTopicname(String topicname);
    Set<Topic> findAll();
}
