package fudan.se.lab2.repository;

import fudan.se.lab2.domain.FirstDiscussion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FirstDiscussionRepository extends CrudRepository<FirstDiscussion,Long> {
Set<FirstDiscussion> findByTitle(String title);
Set<FirstDiscussion>findByTitleAndSpeaker(String title,String speaker);
}
