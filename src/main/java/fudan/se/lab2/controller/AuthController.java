package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.service.AuthService;
import fudan.se.lab2.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LBW
 * 前端文件中，register.vue和login.vue
 *  this.$axios.post('/register', {
 *               username: this.registerForm.username,
 *               password: this.registerForm.password,
 *               fullname: this.registerForm.fullname,
 *               authorities: [this.registerForm.usertype]
 *             })
 *  this.$axios.post('/login', {
 *         username: this.loginForm.username,
 *         password: this.loginForm.password
 *       })
 *
 *  关于RespnseEntity.ok()
 *  ResponseEntity.ok源码
 * //无参ok
 * public static ResponseEntity.BodyBuilder ok() {
 *         return status(HttpStatus.OK);
 *     }
 *
 * //有参ok
 * public static <T> ResponseEntity<T> ok(T body) {
 *         ResponseEntity.BodyBuilder builder = ok();
 *         return builder.body(body);
 *
 * 通过源码，我们不难发现
 * * 与API中的描述一致，无参ok方法返回OK状态，有参ok方法返回body内容和OK状态
 * * body类型 是 泛型T，也就是我们不确定body是什么类型，可以向ok方法传递任意类型的值
 * * 有参ok方法其实有调用无参ok方法
 * ResponseEntity可以通过这个builder返回任意类型的body内容
 */
@RestController
@CrossOrigin
public class AuthController {

    private AuthService authService;


    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService,FileService fileService) {

        this.authService = authService;

    }

    /**
     * @PostMapping
     * 映射一个POST请求
     *
     * Spring MVC新特性
     * 提供了对Restful风格的支持
     *
     * @GetMapping，处理get请求
     * @PostMapping，处理post请求
     * @PutMapping，处理put请求
     * @DeleteMapping，处理delete请求
     *
     * @PostMapping(value = "/user/login")
     *
     *     1
     *
     * 等价于
     *
     * @RequestMapping(value = "/user/login",method = RequestMethod.POST)
     *
     * @param request
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("get a register post");
        logger.debug("RegistrationForm: " + request.toString());

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("get a login post");
        logger.debug("LoginForm: " + request.toString());
        return ResponseEntity.ok(authService.login(request));    //有参ok 返回HttpStatus状态码和body内容
    }

    @PostMapping("/contact")
    public ResponseEntity<?> meeting(@RequestBody MeetingRequest request){
        logger.debug("get a meeting post");
        logger.debug("MeetingForm: " + request.toString());
        return ResponseEntity.ok(authService.meeting(request));
    }


    @PostMapping("/personalInformation")
    public ResponseEntity<?> personalInformation(@RequestBody PersonalInformationRequest request){
        logger.debug("get a personal information request");
        logger.debug("PersonalInformationForm: "+request.toString());
        return ResponseEntity.ok(authService.personalInformation(request));
    }

    @PostMapping(value={"/contactInformation","/searchMeeting"})
    public ResponseEntity<?> contactInformation(@RequestBody ContactInformationRequest request){
        logger.debug("get a contact information/searchMeeting request");
        logger.debug("contactInformationForm: "+request.toString());
        return ResponseEntity.ok(authService.contactInformation(request));
    }

    @PostMapping("/openMeeting")
    public ResponseEntity<?> openMeeting(@RequestBody ContactInformationRequest request){
        logger.debug("get a open meeting request");
        logger.debug("openMeetingForm: "+request.toString());
        return ResponseEntity.ok(authService.openMeeting(request));
    }

    @PostMapping("/personal")
    public ResponseEntity<?> personal(@RequestBody PersonalInformationRequest request){
        logger.debug("get a personal request");
        logger.debug("PersonalForm: "+request.toString());
        return ResponseEntity.ok(authService.personal(request));
    }

    @PostMapping("/searchUser")
    public ResponseEntity<?> searchUser(@RequestBody SearchUserRequest request){
        logger.debug("get a search user request");
        logger.debug("SearchUserForm: "+request.toString());
        return ResponseEntity.ok(authService.searchUser(request));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> admin(@RequestBody AdminRequest request){
        logger.debug("get a admin request");
        logger.debug("AdminForm: "+request.toString());
        return ResponseEntity.ok(authService.admin(request));
    }

    @PostMapping("/passContact")
    public ResponseEntity<?> passContact(@RequestBody PassOrRefuseRequest request){
        logger.debug("get a pass request");
        logger.debug("PassForm: "+request.toString());
        return ResponseEntity.ok(authService.passContact(request));
    }

    @PostMapping("/refuseContact")
    public ResponseEntity<?> refuseContact(@RequestBody PassOrRefuseRequest request){
        logger.debug("get a refuse request");
        logger.debug("RefuseForm: "+request.toString());
        return ResponseEntity.ok(authService.refuseContact(request));
    }

    @PostMapping("/sendInvitation")
    public ResponseEntity<?> sendInvitation(@RequestBody InvitationRequest request){
        logger.debug("get a sendInvitation request");
        logger.debug("sendInvitationForm: "+request.toString());
        return ResponseEntity.ok(authService.sendInvitation(request));
    }

    @PostMapping("/PCmember")
    public ResponseEntity<?> PCmember(@RequestBody InvitationRequest request){
        logger.debug("get a PCmember request");
        logger.debug("PCmemberForm: "+request.toString());
        return ResponseEntity.ok(authService.PCmember(request));
    }

    /**hml的修改区间
     *
     * @param request
     * @return
     */

    @PostMapping("/audit")
    public ResponseEntity<?>audit(@RequestBody AuditRequest request) {
        logger.debug("get a audit post");
        logger.debug("AuditForm: " + request.toString());
        return ResponseEntity.ok(authService.auditInformation(request));
    }
    //查询自己提出的会议申请
    @PostMapping("/userContactState")
    public ResponseEntity<?> userContactState(@RequestBody UserContactStateRequest request){
        logger.debug("get a userContactState post");
        logger.debug("UserContactState Form(search for meetingInline): "+request.toString());
        return ResponseEntity.ok(authService.userContactStateInformation(request));
    }
    @PostMapping("/receivedInvitation")
    public ResponseEntity<?>invitation(@RequestBody InvitationReceivedRequest request){
        logger.debug("get a receivedInvitation post(search for invites received)");
        logger.debug("Invites Form(search for invitation received): "+request.toString());
        return ResponseEntity.ok(authService.invitationReceivedInformation(request));
    }

    @PostMapping("/refuseInvitation")
    public ResponseEntity<?>refuseInvitation(@RequestBody RefuseInvitationRequest request){
        logger.debug("get a refuseInvitation post");
        logger.debug("refuseInvitationForm: "+request.toString());
        return ResponseEntity.ok(authService.refuseInvitation(request));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2020 Software Engineering Lab2. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}



