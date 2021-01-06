package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.DoctorSelectRequest;
import fudan.se.lab2.controller.request.OnlyNameRequest;
import fudan.se.lab2.controller.request.OperateNurseRequest;
import fudan.se.lab2.service.HNurseService;
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
public class HNurseController {
    private HNurseService hNurseService;
    Logger logger = LoggerFactory.getLogger(HNurseController.class);

    @Autowired
    public HNurseController(HNurseService hNurseService) {
        this.hNurseService = hNurseService;
    }

    @PostMapping("/headNurse")
    public ResponseEntity<?> headNurse(@RequestBody OnlyNameRequest onlyNameRequest){
        logger.debug("get a headNurse Request ");

        System.out.println("get a emergencyNurse Request ");
        return ResponseEntity.ok(hNurseService.initialHNurse(onlyNameRequest.getUsername()));    //有参ok 返回HttpStatus状态码和body内容

    }
    /*
    @PostMapping("/select")
    public ResponseEntity<?>select(@RequestBody DoctorSelectRequest doctorSelectRequest){
        logger.debug("get a doctor select Request ");
        System.out.println("get a doctor select Request");
        //int type, int leave,int trans,int status
        return ResponseEntity.ok(hNurseService.select(doctorSelectRequest.getArea_type(),
                doctorSelectRequest.getLeave(),
                doctorSelectRequest.getTrans(),
                doctorSelectRequest.getStatus()));    //有参ok 返回HttpStatus状态码和body内容

    }
*/
    @PostMapping("/deleteNurse")
    public ResponseEntity<?> deleteNurse(@RequestBody OperateNurseRequest operateNurseRequest){
        logger.debug("get a deleteNurse Request ");

        System.out.println("get a deleteNurse Request ");
        return ResponseEntity.ok(hNurseService.deleteNurse(operateNurseRequest.getNurseName(), operateNurseRequest.getArea_type()));
    }

    @PostMapping("/addNurse")
    public ResponseEntity<?> addNurse(@RequestBody OperateNurseRequest operateNurseRequest){
        logger.debug("get a addNurse Request ");

        System.out.println("get a addNurse Request ");
        return ResponseEntity.ok(hNurseService.addNurse(operateNurseRequest.getNurseName(), operateNurseRequest.getArea_type()));
    }
}
