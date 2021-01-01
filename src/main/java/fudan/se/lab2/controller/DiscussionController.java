package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.service.DiscussionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DiscussionController {
    private DiscussionService discussionService;
    Logger logger = LoggerFactory.getLogger(DiscussionController.class);

    @Autowired
    public DiscussionController(DiscussionService discussionService){
        this.discussionService = discussionService;
    }

    @PostMapping("/addFirstDiscussionCommentToBackend")
    public ResponseEntity<?> addFirstDiscussionCommentToBackend(@RequestBody DiscussionCommentRequest discussionCommentRequest){
        String essayTitle = discussionCommentRequest.getEssayTitle();
        String meetingFullname = discussionCommentRequest.getMeetingFullname();

        String speaker = discussionCommentRequest.getSpeaker();
        String content =discussionCommentRequest.getContent();
        logger.debug("get a addFirstDiscussionCommentToBackend request!");

        return ResponseEntity.ok(discussionService.addFirstDiscussionCommentToBackend(meetingFullname,essayTitle,speaker,content));

    }

    @PostMapping("/addSecondDiscussionCommentToBackend")
    public ResponseEntity<?> addSecondDiscussionCommentToBackend(@RequestBody DiscussionCommentRequest discussionCommentRequest){
        String essayTitle = discussionCommentRequest.getEssayTitle();
        String meetingFullname = discussionCommentRequest.getMeetingFullname();

        String speaker = discussionCommentRequest.getSpeaker();
        String content =discussionCommentRequest.getContent();
        logger.debug("get a addSecondDiscussionCommentToBackend request!");

        return ResponseEntity.ok(discussionService.addSecondDiscussionCommentToBackend(meetingFullname,essayTitle,speaker,content));

    }

    @PostMapping("/sendFirstCommentChangeInformation")
    public ResponseEntity<?> sendFirstCommentChangeInformation(@RequestBody CommentRequest request){
        logger.debug("get a sendFirstCommentChangeInformation request!");
        logger.debug("PCmember change comment in first discussion stage");
        return ResponseEntity.ok(discussionService.sendFirstCommentChangeInformation(request));
    }

    @PostMapping("/sendSecondCommentChangeInformation")
    public ResponseEntity<?> sendSecondCommentChangeInformation(@RequestBody CommentRequest request){
        logger.debug("get a sendSecondCommentChangeInformation request!");
        logger.debug("PCmember change comment in second discussion stage");
        return ResponseEntity.ok(discussionService.sendSecondCommentChangeInformation(request));
    }
    @PostMapping("/rebuttal")
    public ResponseEntity<?> rebuttal(@RequestBody RebuttalRequest rebuttalRequest){
        logger.debug("get a rebuttal request!");
        logger.debug("user "+rebuttalRequest.getUsername()+" send a rebuttal request");
        return ResponseEntity.ok(discussionService.rebuttal(rebuttalRequest.getEssayTitle(), rebuttalRequest.getRebuttalText()));
    }
    @PostMapping("/doNotChangeInFirstDiscussion")
    public ResponseEntity<?> doNotChangeInFirstDiscussion(@RequestBody ConfirmCommentRequest confirmCommentRequest){
        logger.debug("get a first discussion comment do not change request");
        logger.debug("PCmember confirm  first comment");

        String essayTitle = confirmCommentRequest.getEssayTitle();
        String pcMemberUsername = confirmCommentRequest.getPcMemberUsername();
        return ResponseEntity.ok(discussionService.doNotChangeInFirstDiscussion(essayTitle,pcMemberUsername));

    }
    @PostMapping("/doNotChangeInSecondDiscussion")
    public ResponseEntity<?> doNotChangeInSecondDiscussion(@RequestBody ConfirmCommentRequest confirmCommentRequest){
        logger.debug("get a second discussion comment do not change request");
        logger.debug("PCmember confirm second comment");
        String essayTitle = confirmCommentRequest.getEssayTitle();
        String pcMemberUsername = confirmCommentRequest.getPcMemberUsername();

        return ResponseEntity.ok(discussionService.doNotChangeInSecondDiscussion(essayTitle,pcMemberUsername));

    }

    @PostMapping(value = "/getFirstDiscussion")
    public ResponseEntity<?>getFirstDiscussion(@RequestBody DiscussionRequest discussionRequest){
        logger.debug("get a first discussion Request");
        logger.debug("getFirstDiscussion");
        String essayTitle = discussionRequest.getEssayTitle();
        return ResponseEntity.ok(discussionService.getFirstDiscussion(essayTitle));
    }
    @PostMapping("/getSecondDiscussion")
    public ResponseEntity<?>getSecondDiscussion(@RequestBody DiscussionRequest discussionRequest){
        logger.debug("get a second discussion Request");
        logger.debug("getSecondDiscussion");
        String essayTitle = discussionRequest.getEssayTitle();
        return ResponseEntity.ok(discussionService.getSecondDiscussion(essayTitle));
    }
}


