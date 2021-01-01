package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeetingRepository meetingRepository;


    @Autowired
    private AuthService authService;

    @Test
    @Transactional
    @Rollback
    void register() {
        //用户名已被注册
        RegisterRequest request = new RegisterRequest("Mary","asfdsgdf","aefsrd",null,null,null);
        try{
            authService.register(request);
            fail("No exception thrown.");
        }catch(Exception ex){
            System.out.println("catch register exception");
            System.out.println(ex.getMessage());
            assertTrue(ex.getMessage().contains("has been registered"));
        }
        //正常注册
        String[] country = {"Shanghai","China"};
        String[] sector = {"","fdu"};
        request = new RegisterRequest("rjgctoday0411","asfdsgdf","aefsrd",null,sector,country);
        assertNotNull(authService.register(request));
    }

    @Test
    void login() {
        //用户正常登录
        LoginRequest request = new LoginRequest();
        request.setUsername("Mary");
        request.setPassword("123456");
        request.setIdentity("user");
        assertNotNull(authService.login(request));

        //密码错误
        request.setPassword("wrong1234");
        try{
            authService.login(request);
            fail("No exception thrown.");
        }catch(Exception ex) {
            System.out.println("catch login wrong password exception");
            assertTrue(ex.getMessage().contains("wrong password"));
        }

        //用户名不存在
        request.setUsername("notfoundname");
        try{
            authService.login(request);
            fail("No exception thrown.");
        }catch(Exception ex){
            System.out.println("catch login not found exception");
            assertTrue(ex.getMessage().contains("Username 'notfoundname' dosen't exist"));
        }

        //管理员正常登录
        //用户正常登录
        request.setUsername("admin1");
        request.setPassword("123456");
        request.setIdentity("admin");
        assertNotNull(authService.login(request));

        //密码错误
        request.setPassword("wrong1234");
        try{
            authService.login(request);
            fail("No exception thrown.");
        }catch(Exception ex) {
            System.out.println("catch admnin login wrong password exception");
            assertTrue(ex.getMessage().contains("wrong password"));
        }

        //用户名不存在
        request.setUsername("notfoundname");
        try{
            authService.login(request);
            fail("No exception thrown.");
        }catch(Exception ex){
            System.out.println("catch admin login not found exception");
            assertTrue(ex.getMessage().contains("Username 'notfoundname' dosen't exist"));
        }
    }

    @Test
    @Transactional
    @Rollback
    void meeting() {

       MeetingRequest request = new MeetingRequest();

        //正常申请会议

        User user = userRepository.findByUsername("Mary Black");
        if(user ==null){
            user = new User("Mary Black", "123456", "Mary Black", "test@qq.com", "Shanghai", "China");
            userRepository.save(user);
        }
        request.setChairname("Mary Black");
        String[] place = {"China","Shanghai"};
        String[]topic = {"health","children","eyesight"};
        request.setShortname("TestMeeting");
        request.setFullname("TestMeeting0411");
        String orgnization = "2020-7-7";
        String resultRelease = "2020-7-5";
        String deadline = "2020-6-30";
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date orgnizationDate = sdf.parse(orgnization);
            Date resultReleaseDate = sdf.parse(resultRelease);
            Date deadlineDate = sdf.parse(deadline);
            request.setOrganizationTime(orgnizationDate);
            request.setResultReleaseTime(resultReleaseDate);
            request.setDeadline(deadlineDate);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }

        request.setPlace(place);
        request.setTopic(topic);
        try{authService.meeting(request);}
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        //assertNotNull(authService.meeting(request));
    }

    @Test
    void personalInformation() {
        PersonalInformationRequest request = new PersonalInformationRequest();

        //用户不存在
        request.setUsername("notFound");
        try{
            authService.personalInformation(request);
            fail("No exception thrown.");
        }catch(Exception ex){
            System.out.println("catch user not found exception");
            assertTrue(ex.getMessage().contains("Username 'notFound' dosen't exist"));
        }

        //正常返回
        request.setUsername("Mary");
        assertNotNull(authService.personalInformation(request));
    }

    @Test
    void auditInformation() {
        AuditRequest request = new AuditRequest("Mary","测试用会议");
        authService.auditInformation(request);
        //
    }

    @Test
    void contactInformation() {
        ContactInformationRequest request = new ContactInformationRequest("National Conference of the American Association for Artificial Intelligence");
        authService.contactInformation(request);
    }
    @Test
    @Transactional
    @Rollback
    void personal(){
        PersonalInformationRequest request = new PersonalInformationRequest("Mary");
        authService.personal(request);
    }
    @Test
    @Transactional
    @Rollback
    void searchUser(){
        SearchUserRequest request = new SearchUserRequest("Lucy King");
        authService.searchUser(request);
    }
    @Test
    @Transactional
    @Rollback
    void admin(){
        AdminRequest adminRequest = new AdminRequest("admin1");
        authService.admin(adminRequest);
    }
    @Test
    @Transactional
    @Rollback
    void passContact(){
        PassOrRefuseRequest refuseRequest = new PassOrRefuseRequest("National Conference of the American Association for Artificial Intelligence","admin1","Mary");
        authService.passContact(refuseRequest);

    }
    @Test
    @Transactional
    @Rollback
    void refuseContact(){
        PassOrRefuseRequest refuseRequest = new PassOrRefuseRequest("National Conference of the American Association for Artificial Intelligence","admin1","Mary");
        authService.refuseContact(refuseRequest);

    }
    @Test
    @Transactional
    @Rollback
    void sendInvitation(){
       String[]inv = {"John","Jack"};
    InvitationRequest request = new InvitationRequest("Mary","National Conference of the American Association for Artificial Intelligence",inv);
       authService.sendInvitation(request);

    }

    @Test
    @Transactional
    @Rollback
    void pcMember(){
        String[]inv = {"John","Jack"};
        InvitationRequest request = new InvitationRequest("Mary","National Conference of the American Association for Artificial Intelligence",inv);
        authService.PCmember(request);

    }
    @Test
    @Transactional
    @Rollback
    void userContactStateInformation(){
       UserContactStateRequest request = new UserContactStateRequest("Mary");
       authService.userContactStateInformation(request);
    }

    @Test
    @Transactional
    @Rollback
    void invitationReceivedInformation(){
        InvitationReceivedRequest request = new InvitationReceivedRequest("John");
    }
    @Test
    void openMeeting() {
        ContactInformationRequest request = new ContactInformationRequest("The World Economic Forum");
        authService.contactInformation(request);
    }

    @Test
    void getReviewStatus(){
        authService.getReviewStatus(1,0,0,"test",-1);
    }
    @Test
    void addFirstDiscussion(){
        authService.addFirstDiscussion("test");
    }
    @Test
    void addSecondDiscussion(){
        authService.addSecondDiscussion("test");
    }
}