package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.*;

import fudan.se.lab2.repository.*;
import fudan.se.lab2.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service

public class FileService {


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

    Logger logger = LoggerFactory.getLogger(FileService.class);



   public Map<String,Object> getHistoryUpload(String title, String meetingFullname){
       Meeting meeting = meetingRepository.findByFullname(meetingFullname);
       if(meeting==null){
           logger.debug("meeting doesn't exist");
           throw new MeetingnameNotFoundException(meetingFullname);
       }
       if(meeting.getMeetingState()!= 2){
           logger.debug("The meeting is not in the submission stage!!!");
           throw new NotInSubmissionStageException(meetingFullname);
       }
       Paper paper = paperRepository.findByTitle(title);
       if(paper ==null){
           logger.debug("paper doesn't exist!!");
           throw new PaperNotFoundException(title);

       }
       Set<Writer> writers = paper.getWritersOfPaper();


       Map<String,Object>map = new HashMap<>();
       map.put("title",title);
       map.put("summary",paper.getSummary());
       /*
           writerName:'第一作者',
    sector:'复旦大学',
    country:'Canada',
    email:'333@qq.com',
        */
       Set<Map>writerSet = new HashSet<>();
       for(Writer writer:writers){
           Map<String,String> tempMap = new HashMap<>();
           tempMap.put("writerName",writer.getWritername());
           tempMap.put("sector",writer.getSector());
           tempMap.put("country",writer.getCountry());
           tempMap.put("email",writer.getEmail());
            writerSet.add(tempMap);

       }
       map.put("writer",writerSet);


       //papertopic
       Set<Topic>topicSet = paper.getTopicsOfPaper();
       List<String>topicList = new ArrayList<>();
       for(Topic topic:topicSet){
           topicList.add(topic.getTopicname());
       }
       //meetingTopic
       Set<Topic>alltopicSet = meeting.getTopicsOfMeeting();
       List<String>alltopicList = new ArrayList<>();
       for(Topic topic:alltopicSet){
           alltopicList.add(topic.getTopicname());
       }
       map.put("topic",topicList);
       map.put("meetingTags",alltopicList);
       return map;
   }


    /**
        有几对关系需要处理
          1. paper和topic的多对多（paper主导）
          2. paper和writer的多对多（paper主导）
          3. meeting和paper的一对多（meeting主导）
          4. meeting和author的多对多（author主导）
    */
    public String Upload(MultipartFile file,
                         String title,
                         String authorname,
                         String[]topic,
                         String summary,
                         String meetingFullname,
                         List<Writer>writers) {

        List<Paper>tempPaperList;

        logger.debug("handling a upload post in FileService");
        logger.debug("title: "+title+" authorname : "+ authorname+" meeting : "+meetingFullname);
       //author 不存在
       if(userRepository.findByUsername(authorname)==null){
           logger.debug("author doesn't exist");
           throw new UsernameNotFoundException(authorname);
       }
       //meeting 不存在
        Meeting meeting = meetingRepository.findByFullname(meetingFullname);
       if(meeting==null){
           logger.debug("meeting doesn't exist");
           throw new MeetingnameNotFoundException(meetingFullname);
       }
       //author 是 chair
       User chair = userRepository.findById(meeting.getChairId()).orElse(null);

           if (chair!=null&&chair.getUsername().equals(authorname)) {
               logger.debug("chair can't submit a paper to his meeting! ");
               throw new ChairConflictWithAuthorException(authorname);
           }


        if(  paperRepository.findByTitle(title)!=null){
            logger.debug("title has been used!!");
            throw new TitleAlreadyExistException(title);

        }


        if(!file.isEmpty()) {
            // 获取文件名称,包含后缀
            String fileName = file.getOriginalFilename();

            // 存放在这个路径下：该路径是该工程目录下的static文件下：(注：该文件可能需要自己创建)
            // 放在static下的原因是，存放的是静态文件资源，即通过浏览器输入本地服务器地址，加文件名时是可以访问到的

              //本地！！
            // String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/";

//             String path = "D:/SE2020/lab2/HardChair-Backend/src/main/resources/static/";
            //云端
//            String path = "/static/";

            // String path = "D:/SE2020/lab2/HardChair-Backend/src/main/resources/static/";
            //云端
            String path = "/usr/local/18302010059/static/";


            try {
                // 该方法是对文件写入的封装，在util类中，导入该包即可使用，后面会给出方法
                FileUtil.fileupload(file.getBytes(), fileName,path);
            } catch (IOException e) {
                logger.error("FileService Upload 方法写入文件异常:",e);
            }

            // 接着创建对应的实体类，将以下路径进行添加，然后通过数据库操作方法写入

            Paper paper = new Paper(authorname,title,summary,meeting);
            //3. meeting和paper的一对多


         paper.setPath("http://114.115.217.166:8080/"+fileName);  //这是云端的位置
//          paper.setPath("http://localhost:8080/"+fileName);//这是本地的地址

            logger.debug("try meeting add Paper");
            //测试

            //1. meeting和author多对多关系，meeting和author都是已有实例，只需添加惯性系即可
            buildRelationBetweenMeetinigAndAuthor(authorname,title,meeting);

            if(((tempPaperList = paperRepository.findByTitleAndAuthorname(title,authorname))!= null)){
                logger.debug("Records : "+tempPaperList.size());
            }
            meeting.addPaper(paper);
            //2. paper和topic的多对多  topic为新
           buildRelationBetweenPaperAndTopic(topic,paper);
            //writer和paper的多对多  writer为新
            buildRelationBetweenWriterAndPaper(writers,paper,title);
            logger.debug("try save meeting first");
            meetingRepository.save(meeting);
            logger.debug("try save Paper");

            if(((tempPaperList = paperRepository.findByTitleAndAuthorname(title,authorname))!= null)){
                logger.debug("Records : "+tempPaperList.size());
            }

            if( paperRepository.findByTitle(title)== null) {
                paperRepository.save(paper);
            }else{
                logger.debug("paper has exist!!!");//有

            }
            logger.debug("topic save finish1!");

            logger.debug("new paper build");


        }
        return "success";

    }
    public void buildRelationBetweenMeetinigAndAuthor(String authorname,String title,Meeting meeting){
        List<Paper>tempPaperList;
        User author = userRepository.findByUsername(authorname);
        logger.debug("try meeting add author");
        meeting.addMeetingAuthor(author);
        logger.debug("try author add meeting");

        author.addMeetingAsAuthor(meeting);
        logger.debug("userRepository save change");
        userRepository.save(author);
        if(((tempPaperList = paperRepository.findByTitleAndAuthorname(title,authorname))!= null)){
            logger.debug("Records : "+tempPaperList.size());
        }
        logger.debug("userRepository save change");
        meetingRepository.save(meeting);
    }
      public void buildRelationBetweenWriterAndPaper(List<Writer>writers,Paper paper,String title){
          for(Writer writer:writers){
              Writer tempWriter = writerRepository.findByWriternameAndEmail(writer.getWritername(),writer.getEmail());
              if(tempWriter!=null){
                  logger.debug("writer exist!");
                  tempWriter.setCountry(writer.getCountry());
                  tempWriter.setSector(writer.getSector());
                  paper.addWritersOfPaper(tempWriter);
                  tempWriter.addPapers(paper);
                  writerRepository.save(tempWriter);
              }else{

                  paper.addWritersOfPaper(writer);
                  writer.addPapers(paper);
                  writerRepository.save(writer);
              }
          }

          if( paperRepository.findByTitle(title)== null) {
              logger.debug("paper doesn't exist!!!");//无
          }
      }
      public void buildRelationBetweenPaperAndTopic(String[]topic,Paper paper){
          for(String topicname:topic) {
              Topic tempTopic;
              if ((tempTopic = topicRepository.findByTopicname(topicname)) == null) {
                  logger.debug("topicRepository try to save new Topic");
                  tempTopic = new Topic(topicname);

              }
              //meeting是@ManyToMany的控制方（写了JoinTable），先加
              paper.addTopics(tempTopic);
              //再加Topic
              tempTopic.addPaper(paper);

              //topic再存
              topicRepository.save(tempTopic);
          }
      }





}
