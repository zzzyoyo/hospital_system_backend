package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.MeetingRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Administrator;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.Topic;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.MeetingnameHasBeenRegisteredException;
import fudan.se.lab2.exception.OutOfTimeException;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.AdministratorRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.TopicRepository;
import fudan.se.lab2.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback
class MeetingApplicationAndStartSubmissionTest {
   @Autowired
   private UserRepository userRepository ;
    @Autowired
    private AuthService authService;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Test
    public void MeetingApplicationAndStartSubmissionTest(){
        RegisterRequest registerRequest1 = new RegisterRequest("newuser1", "123456", "newuser1", null, null, null);
        String[] country = {"", "China"};
        String[] sector = {"", "Shanghai"};
        registerRequest1.setCountry(country);
        registerRequest1.setSector(sector);

        User user1 = userRepository.findByUsername("newuser1");
        if (user1 == null) {
            authService.register(registerRequest1);
        }
        Administrator admin = administratorRepository.findByName("newadmin1");
        if (admin == null) {
            admin = new Administrator("newadmin1");
            administratorRepository.save(admin);
        }
     Meeting meeting = meetingRepository.findByFullname("TestMeeting");
        if(meeting!=null){
            meetingRepository.delete(meeting);
        }
        meeting = meetingRepository.findByFullname("TestMeeting1");
        if(meeting!=null){
            meetingRepository.delete(meeting);
        }
        meeting = meetingRepository.findByShortname("TestMeeting");
        if(meeting!=null){
            meetingRepository.delete(meeting);
        }
        //开始申请会议
        MeetingRequest meetingRequest = new MeetingRequest();

        meetingRequest.setChairname("newuser1");
        String[] place = {"China","Shanghai"};
        String[]topic = {"health","children","eyesight"};
        meetingRequest.setShortname("TestMeeting");
        meetingRequest.setFullname("TestMeeting");
        String orgnization = "2020-7-7";
        String resultRelease = "2020-7-5";
        String deadline = "2020-6-30";

        for(String stringTopic:topic){
            Topic t = new Topic(stringTopic);
            if((topicRepository.findByTopicname(stringTopic))==null)
            topicRepository.save(t);
        }
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date orgnizationDate = sdf.parse(orgnization);
            Date resultReleaseDate = sdf.parse(resultRelease);
            Date deadlineDate = sdf.parse(deadline);
            meetingRequest.setOrganizationTime(orgnizationDate);
            meetingRequest.setResultReleaseTime(resultReleaseDate);
            meetingRequest.setDeadline(deadlineDate);
        }catch(ParseException ex){

        }
        meetingRequest.setPlace(place);
         meetingRequest.setTopic(topic);
         System.out.print("正常申请格式:  ");
         authService.meeting(meetingRequest);

         //会议全称被使用

         MeetingRequest meetingRequest1 = new MeetingRequest();
        meetingRequest1.setChairname("newuser1");
        meetingRequest1.setShortname("TestMeeting1");
        meetingRequest1.setFullname("TestMeeting");
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date orgnizationDate = sdf.parse(orgnization);
            Date resultReleaseDate = sdf.parse(resultRelease);
            Date deadlineDate = sdf.parse(deadline);
            meetingRequest1.setOrganizationTime(orgnizationDate);
            meetingRequest1.setResultReleaseTime(resultReleaseDate);
            meetingRequest1.setDeadline(deadlineDate);
        }catch(ParseException e){

        }
        meetingRequest1.setPlace(place);
        meetingRequest1.setTopic(topic);
        Assertions.assertThrows(MeetingnameHasBeenRegisteredException.class,()->{
            System.out.print("会议全称被使用 ： ");
            authService.meeting(meetingRequest1);
        });

        //会议简称被使用
        meetingRequest1.setFullname("TestMeeting1");
        meetingRequest1.setShortname("TestMeeting");
        Assertions.assertThrows(MeetingnameHasBeenRegisteredException.class,()->{
            System.out.print("会议全称被使用 ： ");
            authService.meeting(meetingRequest1);
        });

        meetingRequest1.setShortname("TestMeeting1");
        meetingRequest1.setChairname("USER");
        Assertions.assertThrows(UsernameNotFoundException.class,()->{
            System.out.print("Chair用户名不存在 ： ");
            authService.meeting(meetingRequest1);
        });

        meetingRequest1.setChairname("newuser1");
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          //  Date orgnizationDate = sdf.parse(orgnization);
            Date resultReleaseDate = sdf.parse(resultRelease);
            Date deadlineDate = sdf.parse(deadline);
         //   meetingRequest1.setOrganizationTime(orgnizationDate);
            meetingRequest1.setResultReleaseTime(deadlineDate);
            meetingRequest1.setDeadline(resultReleaseDate);
        }catch (ParseException E){

        }
        Assertions.assertThrows(OutOfTimeException.class,()->{
            System.out.print("一次公示时间在ddl之前 ： ");
            authService.meeting(meetingRequest1);
        });

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date orgnizationDate = sdf.parse(orgnization);
            Date resultReleaseDate = sdf.parse(resultRelease);
            Date deadlineDate = sdf.parse(deadline);
             meetingRequest1.setOrganizationTime(resultReleaseDate);
            meetingRequest1.setResultReleaseTime(orgnizationDate);
            meetingRequest1.setDeadline(deadlineDate);
        }catch(ParseException e){

        }
        Assertions.assertThrows(OutOfTimeException.class,()->{
            System.out.print("线下会议时间在一次公式时间之前 ： ");
            authService.meeting(meetingRequest1);
        });

    }
}
