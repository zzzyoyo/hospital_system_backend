package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.AddPatientRequest;
import fudan.se.lab2.controller.request.OnlyNameRequest;
import fudan.se.lab2.service.DoctorService;
import fudan.se.lab2.service.ENurseService;
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
public class ENurseController {
    private ENurseService eNurseService;
    Logger logger = LoggerFactory.getLogger(ENurseController.class);

    @Autowired
    public ENurseController(ENurseService eNurseService){
        this.eNurseService = eNurseService;

    }

    @PostMapping("/registerPatient")
    public ResponseEntity<?> registerPatient(@RequestBody AddPatientRequest addPatientRequest){

        logger.debug("get a add Request ");

        System.out.println("get a add Request ");
        return ResponseEntity.ok(eNurseService.addPatient(addPatientRequest));    //有参ok 返回HttpStatus状态码和body内容

    }
}
