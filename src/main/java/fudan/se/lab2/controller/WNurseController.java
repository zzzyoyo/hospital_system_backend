package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.OnlyNameRequest;
import fudan.se.lab2.controller.request.WardNurseSelectRequest;
import fudan.se.lab2.service.WNurseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class WNurseController {
    private WNurseService wNurseService;
    Logger logger = LoggerFactory.getLogger(WNurseController.class);

    @Autowired
    WNurseController(WNurseService wNurseService){
        this.wNurseService = wNurseService;
    }

    @PostMapping("/wardNurse")
    public ResponseEntity<?> initalWNurse(@RequestBody OnlyNameRequest onlyNameRequest){
        logger.debug("get a ward nurse initial Request ");

        String username = onlyNameRequest.getUsername();
        System.out.println("get a ward nurse initial Request");
        return ResponseEntity.ok(wNurseService.initialWardNurse(username));
    }
    @PostMapping("/selectFromMyPatient")
    public ResponseEntity<?> selectFromMyPatient(@RequestBody WardNurseSelectRequest wardNurseSelectRequest){
        logger.debug("get a ward nurse initial Request ");
        System.out.println("get a ward nurse initial Request");
        return ResponseEntity.ok(wNurseService.select(wardNurseSelectRequest.getArea_type(),
                wardNurseSelectRequest.getWardNurseName(),
                wardNurseSelectRequest.getLeave(),
                wardNurseSelectRequest.getTrans(),
                wardNurseSelectRequest.getStatus()));
    }
}
