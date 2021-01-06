package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.AddPatientRequest;
import fudan.se.lab2.controller.request.OnlyNameRequest;
import fudan.se.lab2.service.DoctorService;
import fudan.se.lab2.service.ENurseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ENurseController {
    private ENurseService eNurseService;
    Logger logger = LoggerFactory.getLogger(ENurseController.class);

    @Autowired
    public ENurseController(ENurseService eNurseService){
        this.eNurseService = eNurseService;

    }
    @PostMapping("/emergencyNurse")
    public ResponseEntity<?>emergencyNurse(@RequestBody OnlyNameRequest onlyNameRequest){
        logger.debug("get a emergencyNurse Request ");

        System.out.println("get a emergencyNurse Request ");
        return ResponseEntity.ok(eNurseService.intialENurse(onlyNameRequest.getUsername()));    //有参ok 返回HttpStatus状态码和body内容

    }

    @PostMapping("/registerPatient")
    public ResponseEntity<?> registerPatient(@RequestBody AddPatientRequest addPatientRequest){

        logger.debug("get a add Request ");

        System.out.println("get a add Request ");
        return ResponseEntity.ok(eNurseService.addPatient(addPatientRequest));    //有参ok 返回HttpStatus状态码和body内容

    }

    /**
     * /selectAll',{
     *   area_type: int,//轻、重、危重区域分别为0、1、2，-1代表不筛选
     *   isolated: int,//表示是否在隔离区，0是，1否，2不筛选
     *   rating: int,//0：轻症 1： 重症 2：危重，3不筛选
     *   status: int//0：住院 1：出院 2：死亡,3不筛选
     * }
     */

    @PostMapping("/selectAll")
    public ResponseEntity<?>selectAll(@RequestParam (name = "area_type") int area_type,
                                      @RequestParam (name = "isolated") int isolated,
                                      @RequestParam (name = "rating") int rating,
                                      @RequestParam (name = "status") int status){
        logger.debug("get a emergencyNurse select ");

        System.out.println("get a emergencyNurse select ");
        return ResponseEntity.ok(eNurseService.selectAll(area_type, isolated, rating, status));    //有参ok 返回HttpStatus状态码和body内容

    }
}
