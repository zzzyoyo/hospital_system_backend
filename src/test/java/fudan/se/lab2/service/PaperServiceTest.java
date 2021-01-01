package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.CommentRequest;
import fudan.se.lab2.repository.MeetingRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback
class PaperServiceTest {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private PaperService paperService;

    @Test
    @Transactional
    @Rollback
    void getMeetingTags() {
        paperService.getMeetingTags("International Conference on Dependable Systems");

    }

    @Test
    @Transactional
    @Rollback
    void getTagsList() {
        paperService.getTagsList();
    }

    @Test
    @Transactional
    @Rollback
    void sendCommentInformation() {
        String[]score = {"","2"};
        String[]confidence = {"high"};
        CommentRequest commentRequest = new CommentRequest("Lucy King","IJCAJ 1","International Joint Conference on Artificial Intelligence",1,score,confidence,"good");
      paperService.sendCommentInformation(commentRequest);
    }
}