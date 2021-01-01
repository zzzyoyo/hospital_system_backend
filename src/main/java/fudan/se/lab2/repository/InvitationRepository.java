package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Invitation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long>{
   ArrayList<Invitation> findByPCmemberUsername(String username);
   ArrayList<Invitation> findByChairUsername(String username);
   ArrayList<Invitation> findByMeetingShortname(String meetingShortname);
}
