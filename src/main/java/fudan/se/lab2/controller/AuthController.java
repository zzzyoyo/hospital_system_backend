package fudan.se.lab2.controller;


import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

    private AuthService authService;


    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {

        this.authService = authService;

    }
   /* @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("get a register post");
        logger.debug("RegistrationForm: " + request.toString());

        return ResponseEntity.ok(authService.register(request));
   */

    @PostMapping("/loginHospitalSystem")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.debug("get a login post");
        logger.debug("LoginForm: " + loginRequest.toString());
        System.out.println("auth controller");
        return ResponseEntity.ok(authService.login(loginRequest));    //有参ok 返回HttpStatus状态码和body内容
    }
}


