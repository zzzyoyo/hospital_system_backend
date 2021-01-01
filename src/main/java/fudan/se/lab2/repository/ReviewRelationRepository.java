package fudan.se.lab2.repository;

import fudan.se.lab2.domain.ReviewRelation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRelationRepository extends CrudRepository<ReviewRelation,Long> {
   Set<ReviewRelation> findByPaperTitle(String title);
   ReviewRelation findByPaperTitleAndPCmemberUsername(String paperTitle,String PCmemberUsername);
   Set<ReviewRelation> findByMeetingFullnameAndAndPCmemberUsername(String meetingFullname,String PCmemberUsername);

}
