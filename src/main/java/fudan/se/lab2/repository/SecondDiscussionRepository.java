package fudan.se.lab2.repository;

import fudan.se.lab2.domain.SecondDiscussion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SecondDiscussionRepository extends CrudRepository<SecondDiscussion,Long> {
    Set<SecondDiscussion> findByTitle(String title);
    Set<SecondDiscussion>findByTitleAndSpeaker(String title,String speaker);
}
