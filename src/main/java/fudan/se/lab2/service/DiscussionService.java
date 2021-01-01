package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.CommentRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.*;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiscussionService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private FirstDiscussionRepository firstDiscussionRepository;
    @Autowired
    private SecondDiscussionRepository secondDiscussionRepository;
    @Autowired
    private ReviewRelationRepository reviewRelationRepository;
    @Autowired
    private RebuttalRepository rebuttalRepository;
    Logger logger = LoggerFactory.getLogger(DiscussionService.class);

    public String addFirstDiscussionCommentToBackend(String meetingFullname,
                                                     String essayTitle,
                                                     String speaker,
                                                     String content){
        Paper paper = paperRepository.findByTitle(essayTitle);
        if(paper ==null){
            logger.debug("paper doesn't exist!!");
            throw new PaperNotFoundException(essayTitle);

        }
        Meeting meeting= meetingRepository.findByFullname(meetingFullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(meetingFullname);
        }
        if(meeting.getMeetingState()!= 3){
            logger.debug("The meeting is not in the first discussion stage!!!");
            throw new NotInFirstDiscussionStageException(meetingFullname);
        }
        logger.debug("add first  discussion:"+content+" to paper "+essayTitle);
        FirstDiscussion firstDiscussion = new FirstDiscussion(essayTitle,content,speaker);
        firstDiscussionRepository.save(firstDiscussion);
        Set<FirstDiscussion>firstDiscussionSet = firstDiscussionRepository.findByTitle(essayTitle);
        for(FirstDiscussion findfirstDiscussion:firstDiscussionSet){

            logger.debug("essay "+essayTitle+" speaker: "+findfirstDiscussion.getSpeaker()+"content: "+findfirstDiscussion.getStatement());

        }
        return "success";
    }
    public String addSecondDiscussionCommentToBackend(String meetingFullname,

                                                     String essayTitle,
                                                     String speaker,
                                                     String content){
        Paper paper = paperRepository.findByTitle(essayTitle);
        if(paper ==null){
            logger.debug("paper doesn't exist!!");
            throw new PaperNotFoundException(essayTitle);

        }
        Meeting meeting= meetingRepository.findByFullname(meetingFullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(meetingFullname);
        }
        if((meeting.getMeetingState()!= 5)&&(meeting.getMeetingState()!= 4)){
            logger.debug("The meeting is not in the second discussion stage!!!");
            throw new NotInSecondDiscussionStageException(meetingFullname);
        }
        logger.debug("add second  discussion:"+content+" to paper "+essayTitle);
       SecondDiscussion secondDiscussion = new SecondDiscussion(essayTitle,content,speaker);
        secondDiscussionRepository.save(secondDiscussion);
        return "success";
    }

    public String sendFirstCommentChangeInformation(CommentRequest request){
        String pcmembername = request.getPcmemberFullName();
        logger.debug(pcmembername);
        String title = request.getPaperTitle();
        logger.debug(title);

        int score = Integer.parseInt(request.getScore()[1]);
        String confidence = request.getConfidence()[0];
        String comments = request.getComments();

        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername(title,pcmembername);
        if(reviewRelation == null){
            logger.debug("Review Assignment of paper "+ title+" to PCmember "+pcmembername +" not Found!!!");
            throw new ReviewRelationNotFoundException(title,pcmembername);
        }
        reviewRelation.setComments(comments);
        reviewRelation.setConfidence(confidence);
        reviewRelation.setScore(score);
        reviewRelation.setReviewStatus(1);
        reviewRelation.setFirstModification(1);
        reviewRelation.setSecondModification(0);
        reviewRelationRepository.save(reviewRelation);

        return "success";
    }

    public String sendSecondCommentChangeInformation(CommentRequest request){
        String pcmembername = request.getPcmemberFullName();
        logger.debug(pcmembername);
        String title = request.getPaperTitle();
        logger.debug(title);

        int score = Integer.parseInt(request.getScore()[1]);
        String confidence = request.getConfidence()[0];
        String comments = request.getComments();
        logger.debug("add comment:"+comments+" to paper "+title);
        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername(title,pcmembername);
        if(reviewRelation == null){
            logger.debug("Review Assignment of paper "+ title+" to PCmember "+pcmembername +" not Found!!!");
            throw new ReviewRelationNotFoundException(title,pcmembername);
        }

        reviewRelation.setComments(comments);
        reviewRelation.setConfidence(confidence);
        reviewRelation.setScore(score);
        reviewRelation.setReviewStatus(1);
        reviewRelation.setFirstModification(1);
        reviewRelation.setSecondModification(1);

        reviewRelationRepository.save(reviewRelation);

        return "success";
    }
    public String rebuttal(String essayTitle,String rebuttalText){
        Rebuttal rebuttal = new Rebuttal(essayTitle,rebuttalText);
        rebuttalRepository.save(rebuttal);
        Paper paper = paperRepository.findByTitle(essayTitle);
        logger.debug(essayTitle);

        if(paper.getRebuttal()==0)
            paper.setRebuttal(1);
        paperRepository.save(paper);
        return "success";
    }
    public String doNotChangeInFirstDiscussion(String essayTitle, String pcMemberUsername){
        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername(essayTitle,pcMemberUsername);
        if(reviewRelation==null){
            logger.debug("review relation between paper"+essayTitle+" and pcMember "+pcMemberUsername+" doesn't exist");
            throw new ReviewRelationNotFoundException(essayTitle,pcMemberUsername);
        }
        reviewRelation.setReviewStatus(1);

        reviewRelation.setFirstModification(1);
        reviewRelation.setSecondModification(0);
        reviewRelationRepository.save(reviewRelation);
        return "success";
    }

    public String doNotChangeInSecondDiscussion(String essayTitle, String pcMemberUsername){
        ReviewRelation reviewRelation = reviewRelationRepository.findByPaperTitleAndPCmemberUsername(essayTitle,pcMemberUsername);
        if(reviewRelation==null){
            logger.debug("review relation between paper"+essayTitle+" and pcMember "+pcMemberUsername+" doesn't exist");
            throw new ReviewRelationNotFoundException(essayTitle,pcMemberUsername);
        }
        reviewRelation.setReviewStatus(1);
        reviewRelation.setFirstModification(1);
        reviewRelation.setSecondModification(1);
        reviewRelationRepository.save(reviewRelation);
        return "success";
    }
    public Map<String, Set>getFirstDiscussion(String essayTitle){
          logger.debug("get first discussion of "+essayTitle);
         Set<FirstDiscussion>firstDiscussionSet = firstDiscussionRepository.findByTitle(essayTitle);
        Map<String,Set>returnMap = new HashMap<>();
        Set<Map>returnSet = new HashSet<>();
        if(firstDiscussionSet.isEmpty()){
            logger.debug("empty");
            returnMap.put("firstDiscussion",returnSet);
            return returnMap;
        }
        for(FirstDiscussion firstDiscussion:firstDiscussionSet){
            Map<String,String> tempMap = new HashMap<>();
            tempMap.put("speaker",firstDiscussion.getSpeaker());
            tempMap.put("content",firstDiscussion.getStatement());
            logger.debug("essay "+essayTitle+" speaker: "+firstDiscussion.getSpeaker()+"content: "+firstDiscussion.getStatement());
            returnSet.add(tempMap);
        }
        returnMap.put("firstDiscussion",returnSet);
        return returnMap;
    }
    public Map<String, Set>getSecondDiscussion(String essayTitle){
        Set<SecondDiscussion>secondDiscussionSet = secondDiscussionRepository.findByTitle(essayTitle);

        Map<String,Set>returnMap = new HashMap<>();
        Set<Map>returnSet = new HashSet<>();
        if(secondDiscussionSet.isEmpty()){
            return returnMap;
        }
        for(SecondDiscussion secondDiscussion:secondDiscussionSet){
            Map<String,String> tempMap = new HashMap<>();
            tempMap.put("speaker",secondDiscussion.getSpeaker());
            tempMap.put("content",secondDiscussion.getStatement());
            logger.debug("essay "+essayTitle+" speaker: "+secondDiscussion.getSpeaker()+"content: "+secondDiscussion.getStatement());

            returnSet.add(tempMap);
        }
        returnMap.put("secondDiscussion",returnSet);

        return returnMap;
    }

}



