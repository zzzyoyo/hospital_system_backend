package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.OnlyNameRequest;
import fudan.se.lab2.controller.request.RatingReviseRequest;
import fudan.se.lab2.controller.request.StatusReviseRequest;
import fudan.se.lab2.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DoctorController{
    private DoctorService  doctorService;
    Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService  = doctorService;

    }
    @PostMapping("/doctor")
    //public ResponseEntity<?>initalDoctor(@RequestParam (required = false)String username){
    public ResponseEntity<?>initalDoctor(@RequestParam (name = "username")String username){
        logger.debug("get a doctor initial Request ");

        System.out.println("get a doctor initial Request");
        return ResponseEntity.ok(doctorService.initialDoctor(username));    //有参ok 返回HttpStatus状态码和body内容

    }
    @PostMapping("/ratingRevise")
//    public ResponseEntity<?>ratingRevise(@RequestParam(name = "patientID")  int patientID,@RequestParam(name ="condition_rating") int condition_rating){
    public ResponseEntity<?>ratingRevise(@RequestBody RatingReviseRequest ratingReviseRequest){
        logger.debug("get a ratingRevise Request ");

        System.out.println("get a ratingRevise Request ");


        return ResponseEntity.ok(doctorService.ratingRevise(ratingReviseRequest.getPatientID(),ratingReviseRequest.getCondition_rating()));    //有参ok 返回HttpStatus状态码和body内容

    }
    @PostMapping("/statusRevise")
//    public ResponseEntity<?>statusRevise(@RequestParam(name = "patientID")  int patientID,@RequestParam(name ="statusRevise") int statusRevise){
    public ResponseEntity<?>statusRevise(@RequestBody StatusReviseRequest statusReviseRequest){
        logger.debug("get a statusRevise Request ");

        System.out.println("get a ratingRevise Request ");


        return ResponseEntity.ok(doctorService.statusRevise(statusReviseRequest.getPatientID(),statusReviseRequest.getLiving_status()));    //有参ok 返回HttpStatus状态码和body内容

    }

    @PostMapping("/dailyStatusRecord")
    public ResponseEntity<?>dailyStatusRecord(@RequestParam(name = "patientID")  int patientID){
        logger.debug("get a dailyStatusRecord Request ");

        System.out.println("get a dailyStatusRecord Request ");


        return ResponseEntity.ok(doctorService.dailyStatusRecord(patientID));    //有参ok 返回HttpStatus状态码和body内容

    }
    @PostMapping("/nucleicAcidTestSheet")
    public ResponseEntity<?>nucleicAcidTestSheet(@RequestParam(name = "patientID")  int patientID){
        logger.debug("get a nucleicAcidTestSheet Request ");

        System.out.println("get a nucleicAcidTestSheet Request ");


        return ResponseEntity.ok(doctorService.nucleicAcidTestSheet(patientID));    //有参ok 返回HttpStatus状态码和body内容

    }


}

