package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.CommentRequest;
import fudan.se.lab2.controller.request.MeetingDetailRequest;
import fudan.se.lab2.service.PaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class PaperController {
    private PaperService paperService;
    Logger logger = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    public PaperController(PaperService paperService){
        this.paperService = paperService;
    }

    @PostMapping("/getMeetingTags")
    public ResponseEntity<?> getMeetingTags(@RequestBody MeetingDetailRequest request) {
        logger.debug("get a getMeetingTags request!");
        logger.debug("getMeetingTags request");
        return ResponseEntity.ok(paperService.getMeetingTags(request.getFullname()));
    }

    @PostMapping("/getTagsList")
    public ResponseEntity<?> getTagsList() {
        logger.debug("get a getTagsList request!");
        logger.debug("getTagsList request");
        return ResponseEntity.ok(paperService.getTagsList());
    }

    @PostMapping("/sendCommentInformation")
    public ResponseEntity<?> sendCommentInformation(@RequestBody CommentRequest request){
        logger.debug("get a sendCommentInformation request!");
        logger.debug("PCmember comment request");
        return ResponseEntity.ok(paperService.sendCommentInformation(request));
    }

}
