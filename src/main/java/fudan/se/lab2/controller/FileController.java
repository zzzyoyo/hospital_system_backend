package fudan.se.lab2.controller;

import com.alibaba.fastjson.JSON;
import fudan.se.lab2.domain.Writer;
import fudan.se.lab2.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin
public class FileController {
    private FileService fileService;
    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }


    @PostMapping("/getHistoryUpload")
    public ResponseEntity<?>getHistoryUpload( @RequestParam("authorname")String authorname,
                                              @RequestParam("title")String title,
                                              @RequestParam("meetingFullname")String meetingFullname){

        logger.debug("get a reUpload request!!");
        logger.debug("serach upload history");
        return  ResponseEntity.ok(fileService.getHistoryUpload(title,meetingFullname));

    }
    @PostMapping(value = "/upload")
   public ResponseEntity<?>upload(@RequestParam("file") MultipartFile file,@RequestParam("title")String title,
                                  @RequestParam("authorname")String authorname,@RequestParam("topic")String[]topic,
                               @RequestParam("summary")String summary,@RequestParam("meetingFullname")String meetingFullname,
                                  @RequestParam("writer")String writer) {
        List<Writer>writers = JSON.parseArray(writer,Writer.class);
        for(Writer tempWriter:writers){
            int len = tempWriter.getSector().length();
            tempWriter.setSector(tempWriter.getSector().substring(5,len-2));
            len = tempWriter.getCountry().length();
            tempWriter.setCountry(tempWriter.getCountry().substring(5,len-2));
           logger.debug(tempWriter.getWritername()+tempWriter.getEmail()+tempWriter.getSector()+tempWriter.getCountry());
        }

            logger.debug("get a upload document!!!");
        logger.debug("upload a paper");


      return ResponseEntity.ok(fileService.Upload(file,title,authorname,topic,summary,meetingFullname,writers));

    }




}
