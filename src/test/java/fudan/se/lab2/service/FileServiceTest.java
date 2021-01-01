package fudan.se.lab2.service;

//import com.sun.xml.internal.ws.api.pipe.ContentType;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.domain.Writer;
import fudan.se.lab2.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback
class FileServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private WriterRepository writerRepository;

    @Autowired
            private FileService fileService;

    Logger logger = LoggerFactory.getLogger(FileService.class);

    @Test
    @Transactional
    @Rollback
    void getHistoryUpload(){
            fileService.getHistoryUpload("ACL1","Association of Computational Linguistics");
    }
    @Test
    @Transactional
    @Rollback

    void upload() {
        String title = "testTitle";
        String authorName ="Jack";
        String[]topic = {"数学建模","通信协议"};
        String summary = "testSummary";
        String meetingFullname = "National Conference of the American Association for Artificial Intelligence";
        Writer writer1 = new Writer("testWriter","Fudan","China","testWriter@fudan.edu.cn");
        Writer writer2 = new Writer("testWriter2","Fudan","China","testWriter2@fudan.edu.cn");
        List<Writer> writerList = new ArrayList<>();
        writerList.add(writer1);
        writerList.add(writer2);
        try {
            for(Writer writer:writerList){
                System.out.println(writer.getWritername());
            }
            File file = new File("D:/Downloads/lecture08.pdf");
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile mfile =new MockMultipartFile("test.pdf", fileInputStream);
            fileService.Upload(mfile,title,authorName,topic,summary,meetingFullname,writerList);
        }catch(FileNotFoundException e){
            logger.error("fileNotFound",e);
        } catch (IOException ex) {
            logger.error("IOException",ex);
        }


    }

    @Test
    void buildRelationBetweenMeetinigAndAuthor() {
        Meeting meeting = meetingRepository.findByFullname("Association of Computational Linguistics");
        fileService.buildRelationBetweenMeetinigAndAuthor("Jack","testESSAY",meeting);
    }

    @Test
    void buildRelationBetweenWriterAndPaper() {
        Writer writer1 = new Writer("testWriter","Fudan","China","testWriter@fudan.edu.cn");
        Writer writer2 = new Writer("testWriter2","Fudan","China","testWriter2@fudan.edu.cn");
        List<Writer> writerList = new ArrayList<>();
        writerList.add(writer1);
        writerList.add(writer2);
        Paper paper = paperRepository.findByTitle("AAAI'S seven's essay");
        fileService.buildRelationBetweenWriterAndPaper(writerList,paper,"AAAI'S seven's essay");

    }

    @Test
    void buildRelationBetweenPaperAndTopic() {
        String[]topic = {"数学建模","通信协议"};
        Paper paper = paperRepository.findByTitle("AAAI'S seven's essay");
fileService.buildRelationBetweenPaperAndTopic(topic,paper);

    }
}