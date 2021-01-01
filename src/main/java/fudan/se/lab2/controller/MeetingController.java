package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MeetingController {
    private MeetingService meetingService;
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public MeetingController(MeetingService meetingService){
        this.meetingService = meetingService;
    }


    @PostMapping("/startReview")
    public ResponseEntity<?>startReview(@RequestBody StartReviewRequest request){
        logger.debug("get a startReview Request");
        logger.debug("chair want to start start Review");

        return ResponseEntity.ok(meetingService.startReview(request));
    }
    @PostMapping("/userEssayState")
    public ResponseEntity<?>userEssayState(@RequestBody UserEssayStateRequest request) {
        logger.debug("author try to check his/her essay!!!");
        logger.debug("upload a paper");
      return ResponseEntity.ok(meetingService.userEssayState(request));

    }
    @PostMapping("/meetingDetail")
    public ResponseEntity<?> meetingDetail(@RequestBody MeetingDetailRequest request) {
        logger.debug("Meeting Controller get a meeting Detail search!!!");
        logger.debug("meeting Detail"+request.toString());
        logger.debug("meeting Detail"+request.toString());
        return ResponseEntity.ok(meetingService.meetingDetail(request));
   /* public ResponseEntity<?> meetingDetail(@RequestParam("fullname") String fullName) {
        logger.debug("Meeting Controller get a meeting Detail search!!!");
        logger.debug("meeting Detail");
        Map<String,Object> response  = new HashMap<>();
        //logger.debug("file size"+file.getSize());
        return ResponseEntity.ok(meetingService.meetingDetail(fullName));*/

   /* ;*/

    }
    //没有写service
    @PostMapping("/essaysData")
    public ResponseEntity<?> essaysData(@RequestBody EssaysDataRequest request){
        logger.debug("Search for all essay");
        logger.debug("get a essayData request");
        return ResponseEntity.ok(meetingService.essaysData(request));

    }
    @PostMapping("/recommand")
    public ResponseEntity<?> recommand(){
        logger.debug("Search for all meeting!!!");
        logger.debug("recommand meeting");
        return ResponseEntity.ok(meetingService.recommand());
    }

    @PostMapping("/judgeRoleInMeeting")
    public ResponseEntity<?>judgeRoleInMeeting(@RequestBody JudgeRoleInMeetingRequest request){
        logger.debug("judge Role In Meeting");
        logger.debug("judgeRoleInMeeting");
        return ResponseEntity.ok(meetingService.judgeRoleInMeeting(request));
    }

    @PostMapping("/releaseResult")
    public ResponseEntity<?> releaseResult(@RequestBody ContactInformationRequest request){
        logger.debug("releaseResult request");
        logger.debug("get a releaseResult request");
        return ResponseEntity.ok(meetingService.releaseResult(request));
    }

    @PostMapping("/releaseSecondResult")
    public ResponseEntity<?> releaseSecondResult(@RequestBody ContactInformationRequest request){
        logger.debug("releaseSecondResult request");
        logger.debug("get a releaseSecondResult request");
        return ResponseEntity.ok(meetingService.releaseSecondResult(request));
    }

    @PostMapping("/endRebuttal")
    public ResponseEntity<?> endRebuttal(@RequestBody ContactInformationRequest request){
        logger.debug("endRebuttal request");
        logger.debug("get a endRebuttal request");
        return ResponseEntity.ok(meetingService.endRebuttal(request));
    }

}

