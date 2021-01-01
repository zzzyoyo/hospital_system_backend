package fudan.se.lab2.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fudan.se.lab2.controller.request.CommentRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.*;
import fudan.se.lab2.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback

class DiscussionServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private ReviewRelationRepository reviewRelationRepository;
    @Autowired
    private FirstDiscussionRepository firstDiscussionRepository;
    @Autowired
    private SecondDiscussionRepository secondDiscussionRepository;
    @Autowired
    private DiscussionService discussionService;

    /**
     * AAAI's first essay : National Conference of the American Association for Artificial Intelligence : John
     * AAAI's second essay : National Conference of the American Association for Artificial Intelligence : John
     * AAAI's third essay : National Conference of the American Association for Artificial Intelligence : John
     * AAAI's fourth essay : National Conference of the American Association for Artificial Intelligence : John
     * AAAI's fifth essay : National Conference of the American Association for Artificial Intelligence : John
     * AAAI'S sixth's essay : National Conference of the American Association for Artificial Intelligence : Jack
     * AAAI'S seven's essay : National Conference of the American Association for Artificial Intelligence : Jack
     * AAAI'S eight's essay : National Conference of the American Association for Artificial Intelligence : Jack
     * AAAI'S night's essay : National Conference of the American Association for Artificial Intelligence : Jack
     * ACL1 : Association of Computational Linguistics : Coco
     * ACL2 : Association of Computational Linguistics : Coco
     * ACL3 : Association of Computational Linguistics : Coco
     * IJCAJ 1 : International Joint Conference on Artificial Intelligence : Mary
     */
    @Test
    public void prepare(){
        User user1 = userRepository.findByUsername("newuser1");
        if(user1 ==null){
            user1 = new User("newuser1", "123456", "newuser1", "newuser1@qq.com", "Shanghai","China" );
            userRepository.save(user1);
            System.out.println("新的user1");
        }
        User user2 = userRepository.findByUsername("newuser2");
        if(user2 ==null){
            user1 = new User("newuser2", "123456", "newuser2", "newuser@2qq.com", "Beijing","China" );
            userRepository.save(user1);
            System.out.println("新的user2");
        }
        //user1来开始会议，user2来投稿

      Meeting meeting = meetingRepository.findByShortname("testMeeting");
        if(meeting ==null){
            meeting = new Meeting();
            meeting.setChair(user1);
            meeting.setPlace("Shanghai");
            meeting.setMeetingState(3);
            meeting.setShortname("testMeeting");
            meeting.setFullname("testMeeting");
            String orgnization = "2020-7-7";
            String resultRelease = "2020-7-5";
            String deadline = "2020-6-30";
           meetingRepository.save(meeting);
           System.out.println("新建会议");
        }
        Paper paper = paperRepository.findByTitle("testPaper");
        if(paper ==null){
            paper = new Paper();
            paper.setTitle("testPaper");
            paper.setAuthorname("newuser2");
            paper.setMeeting(meeting);
            paperRepository.save(paper);
            System.out.println("新建论文");
            meeting.addPaper(paper);
            meetingRepository.save(meeting);
        }
        if((paper = paperRepository.findByTitle("testPaper"))==null)
            System.out.println("论文任然不存在");


    }

    @Test
    void addFirstDiscussionCommentToBackend() {
        Meeting meeting = meetingRepository.findByShortname("testMeeting");
        meeting.setMeetingState(3);
        meetingRepository.save(meeting);
       /* Set<Paper> allPapers = paperRepository.findAll();
        for(Paper paper: allPapers){
            System.out.println(paper.getTitle()+" : "+paper.getMeeting().getFullname()+" : "+paper.getAuthorname());
        }*/
       //.addFirstDiscussionCommentToBackend("National Conference of the American Association for Artificial Intelligence","John","AAAI's first essay","Jack","blablabla(first)");
        discussionService.addFirstDiscussionCommentToBackend("testMeeting","testPaper","newuser1","blablabla1");
        Assertions.assertThrows(PaperNotFoundException.class,()->{
            System.out.print("论文不存在情况:  ");
            discussionService.addFirstDiscussionCommentToBackend("testMeeting","testPaper1","newuser1","blablabla1");
        });
        Assertions.assertThrows( MeetingnameNotFoundException.class,()->{
            System.out.print("会议不存在情况:  ");
            discussionService.addFirstDiscussionCommentToBackend("testMeeting1","testPaper","newuser1","blablabla1");
        });
       meeting = meetingRepository.findByShortname("testMeeting");
        meeting.setMeetingState(1);
        meetingRepository.save(meeting);
        Assertions.assertThrows( NotInFirstDiscussionStageException.class,()->{
            System.out.print("不在第一次讨论期间情况:  ");
            discussionService.addFirstDiscussionCommentToBackend("testMeeting","testPaper","newuser1","blablabla1");
        });
    }

    @Test
    void addSecondDiscussionCommentToBackend() {
        Meeting meeting = meetingRepository.findByShortname("testMeeting");
        meeting.setMeetingState(5);
        meetingRepository.save(meeting);

        discussionService.addSecondDiscussionCommentToBackend("testMeeting","testPaper","newuser1","blablabla2");
        Assertions.assertThrows(PaperNotFoundException.class,()->{
            System.out.print("论文不存在情况:  ");
            discussionService.addSecondDiscussionCommentToBackend("testMeeting","testPaper1","newuser1","blablabla2");
        });
        Assertions.assertThrows( MeetingnameNotFoundException.class,()->{
            System.out.print("会议不存在情况:  ");
            discussionService.addSecondDiscussionCommentToBackend("testMeeting1","testPaper","newuser1","blablabla2");
        });

        meeting.setMeetingState(1);
        meetingRepository.save(meeting);
        Assertions.assertThrows( NotInSecondDiscussionStageException.class,()->{
            System.out.print("不在第二次讨论期间情况:  ");
            discussionService.addSecondDiscussionCommentToBackend("testMeeting","testPaper","newuser1","blablabla2");
        });
    }

    @Test
    void sendFirstCommentChangeInformation() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPcmemberFullName("newuser1");
        commentRequest.setPaperTitle("testPaper");
        commentRequest.setMeetingFullname("testMeeting");
        String[]score = {"0","2"};
        String[]confidence = {"low","high"};
        commentRequest.setReviewStatus(1);
        commentRequest.setComments("blablablabla");
        commentRequest.setConfidence(confidence);
        commentRequest.setScore(score);
        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername("testPaper","newuser1");
       if(reviewRelation==null) {
           reviewRelation = new ReviewRelation();
       }
            reviewRelation.setPaperTitle("testPaper");
            reviewRelation.setPCmemberUsername("newuser1");

            reviewRelationRepository.save(reviewRelation);
       // }
        discussionService.sendFirstCommentChangeInformation(commentRequest);

        System.out.print("没有对应评审关系： ");
        commentRequest.setPaperTitle("testPaper3");
        Assertions.assertThrows(ReviewRelationNotFoundException.class,()->{
        discussionService.sendFirstCommentChangeInformation(commentRequest);
        });

    }

    @Test
    void sendSecondCommentChangeInformation() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPcmemberFullName("newuser1");
        commentRequest.setPaperTitle("testPaper");
        commentRequest.setMeetingFullname("testMeeting");
        String[]score = {"0","2"};
        String[]confidence = {"low","high"};
        commentRequest.setReviewStatus(1);
        commentRequest.setComments("blablablabla");
        commentRequest.setConfidence(confidence);
        commentRequest.setScore(score);
        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername("testPaper","newuser1");
        if(reviewRelation==null) {
            reviewRelation = new ReviewRelation();
        }
            reviewRelation.setPaperTitle("testPaper");
            reviewRelation.setPCmemberUsername("newuser1");

            reviewRelationRepository.save(reviewRelation);

        discussionService.sendSecondCommentChangeInformation(commentRequest);

        System.out.print("没有对应评审关系： ");
        commentRequest.setPaperTitle("testPaper3");
        Assertions.assertThrows(ReviewRelationNotFoundException.class,()->{
            discussionService.sendSecondCommentChangeInformation(commentRequest);
        });

    }

    @Test
    void rebuttal() {
        discussionService.rebuttal("testPaper","rebuttalTest");

    }

    @Test
    void doNotChangeInFirstDiscussion() {
        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername("testPaper","newuser1");
        if(reviewRelation==null){
            reviewRelation = new ReviewRelation();

        }
        reviewRelation.setPaperTitle("testPaper");
        reviewRelation.setPCmemberUsername("newuser1");

        reviewRelationRepository.save(reviewRelation);
        discussionService.doNotChangeInFirstDiscussion("testPaper","newuser1");
        Assertions.assertThrows(ReviewRelationNotFoundException.class,()->{
            discussionService.doNotChangeInFirstDiscussion("testPaper12","newuser1");

        });
    }

    @Test
    void doNotChangeInSecondDiscussion() {
        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername("testPaper","newuser1");
        if(reviewRelation==null){
            reviewRelation = new ReviewRelation();

        }
        reviewRelation.setPaperTitle("testPaper");
        reviewRelation.setPCmemberUsername("newuser1");

        reviewRelationRepository.save(reviewRelation);
        discussionService.doNotChangeInSecondDiscussion("testPaper","newuser1");
        Assertions.assertThrows(ReviewRelationNotFoundException.class,()->{
            discussionService.doNotChangeInSecondDiscussion("testPaper12","newuser1");

        });
    }

    @Test
    void getFirstDiscussion() {
        FirstDiscussion firstDiscussion = new FirstDiscussion("testPaper","blabalbla","John");
        firstDiscussionRepository.save(firstDiscussion);
        discussionService.getFirstDiscussion("testPaper");
    }

    @Test
    void getSecondDiscussion() {
       SecondDiscussion secondDiscussion= new SecondDiscussion("testPaper","blabalbla","John");
        secondDiscussionRepository.save(secondDiscussion);
        discussionService.getSecondDiscussion("testPaper");
    }
}