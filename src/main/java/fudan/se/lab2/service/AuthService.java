package fudan.se.lab2.service;


import fudan.se.lab2.controller.request.AddPatientRequest;
import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.BadCredentialsException;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private DoctorRepository doctorRepository;
    private HeadNurseRepository headNurseRepository;
    private WardNurseRepository wardNurseRepository;
    private EmergencyNurseRepository emergencyNurseRepository;
    private PatientRepository patientRepository;
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(DoctorRepository doctorRepository,
                       HeadNurseRepository headNurseRepository,
                       WardNurseRepository wardNurseRepository,
                       EmergencyNurseRepository emergencyNurseRepository,
                       PatientRepository patientRepository){
        this.doctorRepository = doctorRepository;
        this.headNurseRepository = headNurseRepository;
        this.wardNurseRepository  = wardNurseRepository;
        this.emergencyNurseRepository = emergencyNurseRepository;
         this.patientRepository = patientRepository;
    }


    public Map<String,String> login(LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String identity = loginRequest.getRole();
        System.out.println("username:  "+username+"   passward:  "+password+"  identity"+identity);
        Medical_personnel user  = null;
        if(identity.equals("doctor")) {
            logger.debug("doctor login");
            System.out.println("doctor login");
            user= doctorRepository.findByUsername(username);
        }else if(identity.equals("headNurse")){
            user = headNurseRepository.findByUsername(username);
            System.out.println("headNurse login");
        }else if(identity.equals("wardNurse")){
            System.out.println("wardNurse login");
            user = wardNurseRepository.findByUsername(username);

        }else if (identity.equals("emergencyNurse")){
            System.out.println("emergency login");
            user = emergencyNurseRepository.findByUsername(username);

        }
        if(user == null){
          throw new UsernameNotFoundException(username);
        }
        if(!user.getPassword().equals(password))
            { logger.debug("wrong password!!");
            System.out.println("right Pass："+user.getPassword()+" wrong pass"+password);
                throw new BadCredentialsException("wrong password");
            }
        logger.debug(" login success");
        JwtConfigProperties jwtConfigProperties = new JwtConfigProperties();
        jwtConfigProperties.setValidity(18000000);
        jwtConfigProperties.setSecret("FdseFdse2020");
        //生成token，给AC，AC返回hashmap，塞进去，否则空指针
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtConfigProperties);
        String token = null;

        if(identity.equals("doctor")) {
           Doctor doctor= new Doctor(user.getUsername(),user.getPassword());
            token =jwtTokenUtil.generateToken(doctor);

        }else if(identity.equals("headNurse")){
            Head_nurse head_nurse = new Head_nurse(user.getUsername(),user.getPassword());
           token = jwtTokenUtil.generateToken(head_nurse);
        }else if(identity.equals("wardNurse")){
            Ward_nurse ward_nurse = new Ward_nurse(username,password);
         token = jwtTokenUtil.generateToken(ward_nurse);

        }else if (identity.equals("emergencyNurse")){
            Emergency_nurse emergency_nurse = new Emergency_nurse(username,password);
             token = jwtTokenUtil.generateToken(emergency_nurse);
        }
        System.out.println("send back");

        Map<String,String> map = new HashMap<>();
        map.put("token",token);

        map.put("userDetails",loginRequest.getUsername());
        return map;


    }

}
