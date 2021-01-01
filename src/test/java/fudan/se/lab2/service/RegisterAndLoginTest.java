package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Administrator;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.BadCredentialsException;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.AdministratorRepository;
import fudan.se.lab2.repository.UserRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
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
class RegisterAndLoginTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private AdministratorRepository administratorRepository;

    //@Rule
   // public ExpectedException thrown= ExpectedException.none();

    @Test
   public  void registerAndLogin() {
        RegisterRequest registerRequest1 = new RegisterRequest("newuser1", "123456", "newuser1", null, null, null);
        RegisterRequest registerRequest2 = new RegisterRequest("newuser1", "123456", "newuser2", null, null, null);
       /*前端已经校验，不存在存在信息不完全情况
        try{
                    authService.register(registerRequest1);
                }catch(Exception ex){
                 System.out.println("注册失败");
                 //   ex.printStackTrace();
                }*/
        String[] country = {"", "China"};
        String[] sector = {"", "Shanghai"};
        registerRequest1.setCountry(country);
        registerRequest1.setSector(sector);

        registerRequest2.setCountry(country);
        registerRequest2.setSector(sector);
        //先确认这是一个新的用户名


        User user1 = userRepository.findByUsername("newuser1");
        if (user1 != null) {
            System.out.println("newuser1 已经注销");
            authService.cancellation("newuser1");
        }
        User user2 = userRepository.findByUsername("newuser2");
        if (user2 != null) {
            System.out.println("newuser2 已经注销");
            authService.cancellation("newuser2");
        }
        System.out.print("用户正常注册： ");
        authService.register(registerRequest1);
        //重复用户名尝试注册
        Assertions.assertThrows(UsernameHasBeenRegisteredException.class, () -> {
            System.out.print("/重复用户名尝试注册： ");
            authService.register(registerRequest2);
        });

        //user的登录
        LoginRequest loginRequest1 = new LoginRequest("newuser1");
        LoginRequest loginRequest2 = new LoginRequest("newuser2");
        LoginRequest loginRequest3 = new LoginRequest("newadmin1");
        LoginRequest loginRequest4 = new LoginRequest("newadmin2");

        System.out.print("用户正常登录： ");
        authService.login(loginRequest1);
        //使用不存在的用户名登录
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            System.out.print("普通用户使用不存在的用户名登录: ");
            authService.login(loginRequest2);
        });
        //使用错误的密码登录
        loginRequest1.setPassword("123");
        Assertions.assertThrows(BadCredentialsException.class, () -> {
            System.out.print("普通用户使用错误密码登录: ");
            authService.login(loginRequest1);
        });

        //administrator 的登录
        Administrator newadmin1 = new Administrator("newadmin1");
        Administrator admin = administratorRepository.findByName("newadmin1");
        if (admin == null) {
            administratorRepository.save(newadmin1);
        }
        admin = administratorRepository.findByName("newadmin2");
        if (admin != null) {
            authService.cancellation("newadmin2");
        }
        loginRequest3.setIdentity("admin");
        loginRequest4.setIdentity("admin");

        //正常管理员登录
        authService.login(loginRequest3);

        //使用不存在的管理员用户名登录
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            System.out.print("管理员使用不存在的用户名登录: ");
            authService.login(loginRequest4);
        });

        //使用错误的密码管理员登录
        loginRequest3.setPassword("123");
        Assertions.assertThrows(BadCredentialsException.class, () -> {
            System.out.print("管理员使用错误密码登录: ");
            authService.login(loginRequest1);
        });
        authService.cancellation("newuser1");
        authService.cancellation("newadmin1");
        user1 = userRepository.findByUsername("newuser1");
        if (user1 == null) {
            System.out.println("newuser1 已经注销");
        }
        admin = administratorRepository.findByName("newadmin1");
        if (admin == null) {
            System.out.println("newadmin1 已经注销");
        }
    }

}