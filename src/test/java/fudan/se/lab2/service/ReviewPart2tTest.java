package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.CommentRequest;
import fudan.se.lab2.controller.request.ContactInformationRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class ReviewPart2tTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private ReviewRelationRepository reviewRelationRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private  MeetingService meetingService;
    @Autowired
    private DiscussionService discussionService;

    @Test
    public void reviewPart2(){
        prepare();
        prepareReviewRelation();
        //准备好了三篇文章,testEssay过了，后两个没过

        //发布结果
        ContactInformationRequest contactInformationRequest = new ContactInformationRequest("testMeeting",new Date());
         meetingService.releaseResult(contactInformationRequest);
         rebuttal();
         discussionEssay1();
         discussionEssay2();




    }
    public void prepare(){
        User user1 = userRepository.findByUsername("newuser1");
        if(user1 ==null){
            user1 = new User("newuser1", "123456", "newuser1", "newuser1@qq.com", "Shanghai","China" );
            userRepository.save(user1);
            System.out.println("新的user1");
        }
        User user2 = userRepository.findByUsername("newuser2");
        if(user2 ==null){
            user2 = new User("newuser2", "123456", "newuser2", "newuser2@qq.com", "Beijing","China" );
            userRepository.save(user2);
            System.out.println("新的user2");
        }
        User user3 = userRepository.findByUsername("newuser3");
        if(user3 ==null){
            user3 = new User("newuser3", "123456", "newuser3", "newuser3@qq.com", "Beijing","China" );
            userRepository.save(user3);
            System.out.println("新的user3");
        }
        User user4 = userRepository.findByUsername("newuser3");
        if(user4 ==null){
            user4 = new User("newuser4", "123456", "newuser4", "newuser4@2qq.com", "Beijing","China" );
            userRepository.save(user4);
            System.out.println("新的user4");
        }
        //user1来开始会议，user4来投稿，user2,user3是PCmember

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
            meeting.addMeetingPCmembers(user2);
            meeting.addMeetingPCmembers(user3);
            meetingRepository.save(meeting);
            System.out.println("新建会议");
        }
        //新建论文
        Paper paper = paperRepository.findByTitle("testPaper9");
        if(paper ==null){
            paper = new Paper();
            paper.setTitle("testPaper9");
            paper.setAuthorname("newuser2");
            paper.setMeeting(meeting);
            paperRepository.save(paper);
            System.out.println("新建论文 paper");
            meeting.addPaper(paper);
            meetingRepository.save(meeting);
        }

       if((paper = paperRepository.findByTitle("testPaper5"))==null)
            System.out.println("论文 testPaper 任然不存在");


        Paper paper1 = paperRepository.findByTitle("testPaper10");
        if(paper1 ==null){
            paper1 = new Paper();
            paper1.setTitle("testPaper10");
            paper1.setAuthorname("newuser2");
            paper1.setMeeting(meeting);
            paperRepository.save(paper1);
            System.out.println("新建论文 paper9");
            meeting.addPaper(paper1);
            meetingRepository.save(meeting);
        }
        Paper paper2 = paperRepository.findByTitle("testPaper10");
        if(paper2 ==null){
            paper2 = new Paper();
            paper2.setTitle("testPaper2");
            paper2.setAuthorname("newuser2");
            paper2.setMeeting(meeting);
            paperRepository.save(paper2);
            System.out.println("新建论文 paper10");
            meeting.addPaper(paper2);
            meetingRepository.save(meeting);
        }

        Paper paper3 = paperRepository.findByTitle("testPaper11");
        if(paper3 ==null){
            paper3 = new Paper();
            paper3.setTitle("testPaper11");
            paper3.setAuthorname("newuser2");
            paper3.setMeeting(meeting);
            paperRepository.save(paper2);
            System.out.println("新建论文 paper11");
            meeting.addPaper(paper3);
            meetingRepository.save(meeting);
        }
    }
    public void prepareReviewRelation(){
        //第一篇论文通过
        ReviewRelation reviewRelation = new ReviewRelation("newuser1","testPaper9","testMeeting",1,1,"good","high");
        ReviewRelation reviewRelation1 = new ReviewRelation("newuser2","testPaper9","testMeeting",1,1,"good","high");
        ReviewRelation reviewRelation2 = new ReviewRelation("newuser3","testPaper9","testMeeting",2,2,"good","very high");
        reviewRelation.setFirstModification(1);
        reviewRelation1.setFirstModification(1);
        reviewRelation2.setFirstModification(1);
        reviewRelationRepository.save(reviewRelation);
        reviewRelationRepository.save(reviewRelation1);
        reviewRelationRepository.save(reviewRelation2);
        //第二篇，第三篇论文不通过
        reviewRelation = new ReviewRelation("newuser1","testPaper10","testMeeting",1,1,"good","high");
        reviewRelation1 = new ReviewRelation("newuser2","testPaper10","testMeeting",1,-1,"not good","low");
         reviewRelation2 = new ReviewRelation("newuser3","testPaper10","testMeeting",2,2,"good","very high");
        reviewRelation.setFirstModification(1);
        reviewRelation1.setFirstModification(1);
        reviewRelation2.setFirstModification(1);
        reviewRelationRepository.save(reviewRelation);
        reviewRelationRepository.save(reviewRelation1);
        reviewRelationRepository.save(reviewRelation2);

        //第二篇，第三篇论文不通过
        reviewRelation = new ReviewRelation("newuser1","testPaper11","testMeeting",1,1,"good","high");
        reviewRelation1 = new ReviewRelation("newuser2","testPaper11","testMeeting",1,-1,"not good","low");
        reviewRelation2 = new ReviewRelation("newuser3","testPaper11","testMeeting",2,-1,"notgood","very low");
        reviewRelation.setFirstModification(1);
        reviewRelation1.setFirstModification(1);
        reviewRelation2.setFirstModification(1);
        reviewRelationRepository.save(reviewRelation);
        reviewRelationRepository.save(reviewRelation1);
        reviewRelationRepository.save(reviewRelation2);


    }
    public void rebuttal(){

      // discussionService.rebuttal("testEssay1","rebuttal11");
       // discussionService.rebuttal("testEssay2","rebuttal12");

    }
    public void discussionEssay1(){
        String[]score = {"1","2"};
        String[]confidence = {"good","verygood"};
        CommentRequest commentRequest = new CommentRequest("newuser2","testPaper11","testMeeting",1,score,confidence,"high");

        discussionService.addSecondDiscussionCommentToBackend("testMeeting","testPaper11","newuser2","blablabla2");
        discussionService.addSecondDiscussionCommentToBackend("testMeeting","testPaper11","newuser1","blablabla1");
        discussionService.getSecondDiscussion("testEssay1");
        discussionService.doNotChangeInSecondDiscussion("testPaper1","newuser1");
        discussionService.sendSecondCommentChangeInformation(commentRequest);
        commentRequest.setPcmemberFullName("newuser3");
        discussionService.sendSecondCommentChangeInformation(commentRequest);

    }
    public void discussionEssay2(){
        String[]score = {"1","2"};
        String[]confidence = {"good","verygood"};
        CommentRequest commentRequest = new CommentRequest("newuser3","testPaper12","testMeeting",1,score,confidence,"high");
        CommentRequest commentRequest1 = new CommentRequest("newuser2","testPaper12","testMeeting",1,score,confidence,"high");
        discussionService.getSecondDiscussion("testEssay2");
        discussionService.sendSecondCommentChangeInformation(commentRequest);
        discussionService.sendSecondCommentChangeInformation(commentRequest1);

    }
}
