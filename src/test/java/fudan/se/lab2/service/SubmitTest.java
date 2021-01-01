package fudan.se.lab2.service;

//import com.oracle.webservices.internal.api.message.ContentType;
import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.BadCredentialsException;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback
class SubmitTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;
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
    @Autowired
    private FileService fileService;
    @Autowired
    private TopicRepository topicRepository;

    //@Rule
   // public ExpectedException thrown= ExpectedException.none();

    @Test
   public  void submit() throws IOException {
        prepare();
        File file = new File("D:\\大二下\\离散数学下\\lecture5\\lecture05.pdf");
        MultipartFile mulFile = new MockMultipartFile(
                "lecture05.pdf",
                new FileInputStream(file)
        );
        String[] topic = {"topic"};
        Writer writer = new Writer("writer","fdu","china","233@qq.com");
        List<Writer> writers = new LinkedList<>();
        writers.add(writer);
        System.out.println("论文上传：");
        fileService.Upload(mulFile,"testUpload","newuser4",topic,"很不错的论文","testSubmitMeeting",writers);
        System.out.println("上传成功！");
    }

    public void prepare(){
        //新建用户
        System.out.println("新建用户：");
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
        User user4 = userRepository.findByUsername("newuser4");
        if(user4 ==null){
            user4 = new User("newuser4", "123456", "newuser4", "newuser4@2qq.com", "Beijing","China" );
            userRepository.save(user4);
            System.out.println("新的user4");
        }
        //新建会议
        Meeting meeting = meetingRepository.findByShortname("testSubmitMeeting");
        if(meeting == null){
            System.out.println("新建会议testSubmitMeeting：");
            meeting = new Meeting();
            meeting.setChair(user1);
            meeting.setChairId(user1.getId());
            meeting.setPlace("Shanghai");
            meeting.setMeetingState(3);
            meeting.setShortname("testSubmitMeeting");
            meeting.setFullname("testSubmitMeeting");
            String orgnization = "2020-7-7";
            String resultRelease = "2020-7-5";
            String deadline = "2020-6-30";
            Topic topic = new Topic("topic");
            if(topicRepository.findByTopicname("topic")==null)
                topicRepository.save(topic);
            meeting.addTopics(topic);
            meeting.addMeetingPCmembers(user2);
            meeting.addMeetingPCmembers(user3);
            meetingRepository.save(meeting);
            System.out.println("新建会议成功");
        }
    }
}