package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.OnlyNameRequest;
import fudan.se.lab2.service.WNurseService;
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
}
