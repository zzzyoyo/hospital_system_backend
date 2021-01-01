package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.CommentRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.MeetingnameNotFoundException;
import fudan.se.lab2.exception.ReviewRelationNotFoundException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class PaperService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private ReviewRelationRepository reviewRelationRepository;

    Logger logger = LoggerFactory.getLogger(FileService.class);
    /**
     * 因为请求参数可能相同，所以不能用参数
     * @param fullname
     * @return
     */

    //@PostMapping("/getMeetingTags")
    public Map<String,List> getMeetingTags(@RequestParam("fullname")String fullname){
        logger.debug("handling getMeetingTags");
        //meeting 不存在
        String meetingFullname = fullname;
        Meeting meeting = meetingRepository.findByFullname(meetingFullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(meetingFullname);
        }
        Set<Topic> topics = meeting.getTopicsOfMeeting();
        List<String>meetingTags = new ArrayList<>();
        for(Topic topic:topics){
           meetingTags.add(topic.getTopicname());
        }
        Map<String ,List>returnMap = new HashMap<>();
        returnMap.put("allMeetingTags",meetingTags);
        return returnMap;
    }

   // @PostMapping("/getTagsList")
    public Map<String,String[]> getTagsList(){
        logger.debug("get getTagsList request");
        Set<Topic> topics = topicRepository.findAll();
        String[] meetingTags = new String[topics.size()];
        int i = 0;
        for(Topic topic:topics){
            meetingTags[i] = topic.getTopicname();
            i++;
        }
        Map<String,String[]> returnMap = new HashMap<>();
        returnMap.put("allTagsInDataBase",meetingTags);
        return returnMap;
    }
    public String sendCommentInformation(CommentRequest request){
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
         reviewRelationRepository.save(reviewRelation);
         return "success";
    }


}
