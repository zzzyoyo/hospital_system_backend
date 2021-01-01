package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.*;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MeetingService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private ReviewRelationRepository reviewRelationRepository;

    @Autowired
    private PCmemberToTopicRepository pCmemberToTopicRepository;

    Logger logger = LoggerFactory.getLogger(MeetingService.class);
    /**
     * 	1. 会议的审稿由该会议的chair在会议管理中手动开启，开启审稿的时候必
     * 	须要保证该会议中至少有两个PC member。如果开启审稿时会议处于可
     * 	投稿状态，则会议自动结束可投稿状态并进入审稿状态
     * 	2. 每篇稿件要3个PC member审稿（其中可以包括Chair），权限见前文，PC member不足提醒Chair PC member不足（message要现实会议名之类的）
//     * @param meetingFullname
//     * @param distributionStrategy
     * @return
     */
    public static final String PCmemberNotEnough = "PCmember 人数不足，分配失败";
    public static final String dataFormat = "yyyy-MM-dd";
    public String startReview(StartReviewRequest request){
        String meetingFullname = request.getContactName();
        int distributionStrategy = request.getWay();
        Meeting meeting = meetingRepository.findByFullname(meetingFullname);
        //判断会议是否存在
        if(meeting ==null){
            logger.debug("meeting doesn't exist!!");
            throw new MeetingnameNotFoundException(meetingFullname);
        }
        //获取当前时间
        Date date = new Date();
        if(date.after(meeting.getResultReleaseTime())){
            logger.debug("Can't start review!! after resultReleaseTime!!!");
            throw new OutOfTimeException(meetingFullname);
        }
        //0代表基于topic,1代表平均分配
        //稿件分配算法
        //meeting下所有paper
        Set<Paper> papersOfMeeting = meeting.getPapers();
        //meeting的所有PCMember
        Set<User> pcmembersOfMeeting = meeting.getMeetingPCmembers();

        //按topic分配
        if(distributionStrategy==0){
            topicAllocate(papersOfMeeting,meeting);
        }

        //平均分配策略
        else if(distributionStrategy==1){
            equalAllocate(papersOfMeeting,pcmembersOfMeeting,meeting);
        }

        meeting.setStartSubmissionTime(date);
        meeting.setMeetingState(3);
        meeting.setDistributionStrategy(distributionStrategy);
        meetingRepository.save(meeting);

        return "success";
    }



    //按topic分配方法
    public void topicAllocate(Set<Paper> papersOfMeeting,Meeting meeting){
        String meetingFullname = meeting.getFullname();
        List<LinkedList<String>> allpcmemberList = new LinkedList<>();
        //先全部判断是否能分配
        for(Paper paper:papersOfMeeting){
            Set<Topic> topics = paper.getTopicsOfPaper();
            LinkedList<String> pcmemberUsernames = new LinkedList<>();
            //paper的writers
            Set<Writer> writers = paper.getWritersOfPaper();
            User author = userRepository.findByUsername(paper.getAuthorname());
            //把所有符合条件的pcmember的username列出来
            for(Topic topic:topics){
                Set<PCmemberToTopic> pCmemberToTopics = pCmemberToTopicRepository.findByMeetingFullnameAndAndTopicname(meetingFullname,topic.getTopicname());
                for(PCmemberToTopic pCmemberToTopic:pCmemberToTopics){
                    if(!pcmemberUsernames.contains(pCmemberToTopic.getUsername())){
                        User TempPCmember = userRepository.findByUsername(pCmemberToTopic.getUsername());
                        boolean ifLimit = false;
                        if(author.getFullname().equals(TempPCmember.getFullname()) && author.getEmail().equals(TempPCmember.getEmail()))
                            ifLimit = true;
                        else {
                            for(Writer writer:writers){
                                if(writer.getWritername().equals(TempPCmember.getFullname()) && writer.getEmail().equals(TempPCmember.getEmail()))
                                    ifLimit = true;
                            }
                        }
                        if(!ifLimit)
                            pcmemberUsernames.add(pCmemberToTopic.getUsername());
                    }
                }
            }
            if(pcmemberUsernames.size()<3){
                logger.debug(PCmemberNotEnough);
                throw new PCmemberNotEnoughException(meetingFullname);
            }
            else
                allpcmemberList.add(pcmemberUsernames);
        }
        //开始分配
        allocate(allpcmemberList,papersOfMeeting,meetingFullname);
    }
    public void allocate(List<LinkedList<String>> allpcmemberList,Set<Paper> papersOfMeeting,String meetingFullname){
        int i = 0;
        for(Paper paper:papersOfMeeting){
            int size = allpcmemberList.get(i).size();
            int[] randomNum = randomNumber(size);    //三个随机数的数组
            for(int j=0;j<randomNum.length;j++){
                ReviewRelation reviewRelation = new ReviewRelation(allpcmemberList.get(i).get(randomNum[j]), paper.getTitle(), meetingFullname);
                reviewRelationRepository.save(reviewRelation);
            }
            i++;
        }
    }
    //topicAllocate中生成随机数的方法
    public int[] randomNumber(int size){
        int[] returnNumber = new int[3];
        int a = (int)(Math.random()*size);

        int b = (int)(Math.random()*size);

        int c = (int)(Math.random()*size);
        while(b == a)
            b = (int)(Math.random()*size);
        while(c==a || c==b)
            c = (int)(Math.random()*size);
        returnNumber[0] = a;
        returnNumber[1] = b;
        returnNumber[2] = c;
        return returnNumber;
    }

    //平均分配方法
    public void equalAllocate(Set<Paper> papersOfMeeting,Set<User> pcmembersOfMeeting,Meeting meeting){
        //工具类
        Set<TempPaper> tempPapers = new HashSet<>();
        for(Paper paper:papersOfMeeting)
            tempPapers.add(new TempPaper(paper,0));
        Set<TempPCmember> tempPCmembers = new HashSet<>();
        for(User user:pcmembersOfMeeting)
            tempPCmembers.add(new TempPCmember(user,0,0));
        //先粗略判断是否能分配
        for(TempPaper tpaper:tempPapers){
            LinkedList<String> pcmemberUsernames = new LinkedList<>();
            //paper的writers
            Set<Writer> writers = tpaper.getPaper().getWritersOfPaper();
            User author = userRepository.findByUsername(tpaper.getPaper().getAuthorname());
            //把所有pcmember的username列出来
            for(TempPCmember tPCmember:tempPCmembers){
                if(!pcmemberUsernames.contains(tPCmember.getPcmember().getUsername())){
                    boolean ifLimit = false;
                    User TempPCmember = userRepository.findByUsername(tPCmember.getPcmember().getUsername());
                    if(author.getFullname().equals(TempPCmember.getFullname()) && author.getEmail().equals(TempPCmember.getEmail())){
                        ifLimit = true;
                        tpaper.setLimitNum(tpaper.getLimitNum()+1);
                        tPCmember.setLimitNum(tPCmember.getLimitNum()+1);
                    }
                    else {
                        for(Writer writer:writers){
                            if(writer.getWritername().equals(TempPCmember.getFullname()) && writer.getEmail().equals(TempPCmember.getEmail())){
                                ifLimit = true;
                                tpaper.setLimitNum(tpaper.getLimitNum()+1);
                                tPCmember.setLimitNum(tPCmember.getLimitNum()+1);
                            } 
                        }
                    }
                    if(!ifLimit)
                        pcmemberUsernames.add(tPCmember.getPcmember().getUsername());
                }
            }
            if(pcmemberUsernames.size()<3){
                logger.debug("PC member 人数不足，分配失败");
                throw new PCmemberNotEnoughException(meeting.getFullname());
            }
        }
        //把paper和pcmember都按限制高低排序
        List<TempPaper> tempPaperList = new LinkedList<>();
        for(TempPaper tpaper:tempPapers){
            if(tempPaperList.isEmpty())
                tempPaperList.add(tpaper);
            else{
                int index = tempPaperList.size();
                for(int i=0;i<tempPaperList.size();i++){
                    if(tpaper.getLimitNum()>tempPaperList.get(i).getLimitNum()){
                        index = i;
                        break;
                    }
                }
                tempPaperList.add(index,tpaper);
            }
        }
        List<TempPCmember> tempPCmemberList = new LinkedList<>();
        for(TempPCmember tPCmember:tempPCmembers){
            if(tempPCmemberList.isEmpty())
                tempPCmemberList.add(tPCmember);
            else{
                int index = tempPCmemberList.size();
                for(int i=0;i<tempPCmemberList.size();i++)
                    if(tPCmember.getLimitNum()>tempPCmemberList.get(i).getLimitNum()){
                        index = i;
                        break;
                    }
                tempPCmemberList.add(index,tPCmember);
            }
        }
        //开始分配
        int largest;
        int largeNum;
        int current = 0;
        if((largeNum=(papersOfMeeting.size()*3)%(pcmembersOfMeeting.size()))==0)
            largest = papersOfMeeting.size()*3/pcmembersOfMeeting.size();
        else
            largest = papersOfMeeting.size()*3/pcmembersOfMeeting.size()+1;
        for(TempPaper tpaper:tempPaperList){
            int pcNum = 0;
            Set<Writer> writers = tpaper.getPaper().getWritersOfPaper();
            for(TempPCmember tPCmember:tempPCmemberList){
                boolean limit = false;
                for(Writer writer:writers){
                    User TempPCmember = userRepository.findByUsername(tPCmember.getPcmember().getUsername());
                    if(writer.getWritername().equals(TempPCmember.getFullname()) && writer.getEmail().equals(TempPCmember.getEmail())){
                        limit = true;
                        break;
                    }
                }
                if(!limit && tPCmember.getPaperNum()<largest && pcNum<3){
                    if(tPCmember.getPaperNum()<largest-1){
                        pcNum++;
                        tpaper.addPcusername(tPCmember.getPcmember().getUsername());
                    }
                    else{
                        if(current<largeNum){
                            current++;
                            pcNum++;
                            tpaper.addPcusername(tPCmember.getPcmember().getUsername());
                        }
                    }
                }
            }
            if(pcNum<3){
                logger.debug("PC member 人数不足，分配失败");
                throw new PCmemberNotEnoughException(meeting.getFullname());
            }
        }
        //把reviewRelation存入数据库
        for(TempPaper tpaper:tempPaperList){
            List<String> pcusernames = tpaper.getPcusername();
            for(String pcname:pcusernames){
                ReviewRelation reviewRelation = new ReviewRelation(pcname,tpaper.getPaper().getTitle(),meeting.getFullname());
                reviewRelationRepository.save(reviewRelation);
            }
        }
    }
    //两个工具类
    class TempPaper{
        private Paper paper;
        private int limitNum;
        private List<String> pcusername;
        TempPaper(){}
        TempPaper(Paper paper,int limitNum){
            this.limitNum = limitNum;
            this.paper = paper;
        }

        public int getLimitNum() {
            return limitNum;
        }

        public Paper getPaper() {
            return paper;
        }

        public void setLimitNum(int limitNum) {
            this.limitNum = limitNum;
        }

        public void setPaper(Paper paper) {
            this.paper = paper;
        }

        public List<String> getPcusername() {
            return pcusername;
        }

        public void setPcusername(List<String> pcusername) {
            this.pcusername = pcusername;
        }
        public void addPcusername(String pcname){
            this.pcusername.add(pcname);
        }
    }
    class TempPCmember{
        private User pcmember;
        private int limitNum;
        private int paperNum;
        TempPCmember(){}
        TempPCmember(User pcmember,int limitNum,int paperNum){
            this.limitNum = limitNum;
            this.pcmember = pcmember;
            this.paperNum = paperNum;
        }

        public int getLimitNum() {
            return limitNum;
        }

        public User getPcmember() {
            return pcmember;
        }

        public void setLimitNum(int limitNum) {
            this.limitNum = limitNum;
        }

        public void setPcmember(User pcmember) {
            this.pcmember = pcmember;
        }

        public int getPaperNum() {
            return paperNum;
        }

        public void setPaperNum(int paperNum) {
            this.paperNum = paperNum;
        }
    }
    public String addEssayStatus(int firstModification,int secondModification,int rebuttal){
        if(firstModification==0 && secondModification==0){
           return "已首次确认";
        }
        if(firstModification==1 && secondModification==0 && rebuttal==-1){
            return "已录用";
        }
        if(firstModification==1 && secondModification==0 && rebuttal==0){
            return "已驳回但未提交rebuttal";
        }
        if(firstModification==1 && secondModification==0 &&rebuttal==1){
         return "已提交rebuttal待再次确认";
        }
        if(firstModification==1 && secondModification==0){
          return "已首次确认";

        }
        if(firstModification==1 && secondModification==1){
            return "已再次确认";
        }
        return "未知状态";
    }

    public Map<String, Set>userEssayState(UserEssayStateRequest request){
        String authorname = request.getUsername();
        String meetingFullname = request.getContactName();

        List<Paper> papers = paperRepository.findByAuthorname(authorname);
        Meeting meeting = meetingRepository.findByFullname(meetingFullname);


        Set<Map> returnSet = new HashSet<>();
        if(papers.isEmpty()){
            logger.debug("you have submitted no paper!");
            throw new NoPaperException(authorname);
        }
        if(meeting == null){
            logger.debug("meeting doesn't exist!!");
            throw new MeetingnameNotFoundException(meetingFullname);
        }
        logger.debug("meetingShortname is "+meeting.getShortname());

        //获取当前作者当前会议的投稿
       Set<Paper>paperForThisMeeting = new HashSet<>();
       for(Paper paper:papers){

               if(paper.getMeeting().getShortname().equals(meeting.getShortname())){
                   paperForThisMeeting.add(paper);
               }
       }
       for(Paper paper:paperForThisMeeting){
           Map<String,Object>tempMap = new HashMap<>();
           int meetingState = paper.getMeeting().getMeetingState();
           tempMap.put("meetingState",meetingState);
           tempMap.put("title",paper.getTitle());
           tempMap.put("summary",paper.getSummary());
           tempMap.put("rebuttalState",paper.getRebuttal());
           if(meetingState==4){
               Date rebuttalDeadline = paper.getMeeting().getRebuttalDeadline();
               SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
               String rebuttalDDL = dateFormat.format(rebuttalDeadline);
               tempMap.put("rebuttalDeadline",rebuttalDDL);
           }
           Set<Map> reviewSet = new HashSet();
           Set<ReviewRelation> reviewRelations = reviewRelationRepository.findByPaperTitle(paper.getTitle());

           for(ReviewRelation reviewRelation:reviewRelations){
               Map<String,String> reviewMap = new HashMap<>();
               if(reviewRelation.getReviewStatus()==0){
                   reviewMap.put("status","待审核");
                   reviewMap.put("score","");
                   reviewMap.put("confidence","");
                   reviewMap.put("comment","");
               }
               else if(reviewRelation.getReviewStatus()==1){
                   reviewMap.put("score",reviewRelation.getScore()+"");
                   reviewMap.put("confidence",reviewRelation.getConfidence());
                   reviewMap.put("comment",reviewRelation.getComments());
                   int firstModification = reviewRelation.getFirstModification();
                   int secondModification = reviewRelation.getSecondModification();
                   int rebuttal = paper.getRebuttal();
                  String essayStatus = addEssayStatus(firstModification,secondModification,rebuttal);
                   reviewMap.put("status",essayStatus);
                   reviewSet.add(reviewMap);
               }
           }
           tempMap.put("commentSet",reviewSet);
           returnSet.add(tempMap);
       }

       Map<String,Set> returnMap = new HashMap<>();
        returnMap.put("userEssayStateInformation",returnSet);
        return returnMap;
    }

    public Map<String, Set> recommand(){
        Map<String,Set> map = new HashMap<>();
       Set<Map>returnSet = new HashSet<>();
        Iterable<Meeting> allMeetings = meetingRepository.findAll();
        logger.debug("all the meetings: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);

        for(Meeting meeting: allMeetings){
            if(meeting.getMeetingState() ==2) {
                ArrayList<String>stringtopics = new ArrayList<>();
                Set<Topic>topics = meeting.getTopicsOfMeeting();
                for(Topic topic:topics){
                    stringtopics.add(topic.getTopicname());
                }

                Map<String, Object> tempMap = new HashMap<>();
                logger.debug(meeting.getShortname());
                tempMap.put("shortname", meeting.getShortname());
                tempMap.put("fullname", meeting.getFullname());
                Date organizationDate = meeting.getOrganizationTime();
                String organizationTime = dateFormat.format(organizationDate);

                Date deadlineDate = meeting.getDeadline();
                String deadlineTime = dateFormat.format(deadlineDate);

                Date releaseResultDate = meeting.getResultReleaseTime();
                String releaseResultTime =   dateFormat.format( releaseResultDate );

                tempMap.put("deadline", deadlineTime);
                tempMap.put("resultReleaseTime",releaseResultTime);
                tempMap.put("organizationTime", organizationTime);

                tempMap.put("place", meeting.getPlace());
                tempMap.put("topic",stringtopics);

                returnSet.add(tempMap);
            }
        }
        map.put("allLegalMeeting",returnSet);
       return map;
    }

    public Map<String, Map> meetingDetail(MeetingDetailRequest request){

        String fullname = request.getFullname();
        logger.debug("meetingDetail fullname is  "+ fullname);
        Map<String,Object> map = new HashMap<>();
        Meeting meeting;
        if((meeting =meetingRepository.findByFullname(fullname))==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(fullname);
        }
        logger.debug("all the topics are");
        Set<Topic> topics = meeting.getTopicsOfMeeting();
        for(Topic topic:topics){
            logger.debug(topic.getTopicname());
        }

        map.put("FullName",fullname);
        User chair = userRepository.findById(meeting.getChairId()).orElse(null);
        if(chair==null){
            logger.debug("chair doesn't exist");
        }else {
            String chairname = chair.getUsername();

            map.put("chair", chairname);
        }

        List<String> pcmemberName = new ArrayList<>();
        Set<User> pcmembers  = meeting.getMeetingPCmembers();
        for(User pcmember:pcmembers){
            pcmemberName.add(pcmember.getUsername());
        }
        map.put("pcMembers",pcmemberName);
        List<String> authorName = new ArrayList<>();
        Set<User> authors = meeting.getMeetingAuthor();
        for(User author:authors){
            authorName.add(author.getUsername());
        }
        map.put("authors",authorName);

        map.put("state",meeting.getMeetingState());
        String meetingShortname = meeting.getShortname();
        map.put("ShortName",meetingShortname);
        SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd ");
        Date organizationDate = meeting.getOrganizationTime();
         String organizationTime = dateFormat.format(organizationDate);
        map.put("BeginTime",organizationTime);
        Date deadlineDate = meeting.getDeadline();
        String deadlineTime = dateFormat.format(deadlineDate);
        map.put("ContributeDDL",deadlineTime);

        Date releaseResultDate = meeting.getResultReleaseTime();
        String releaseResultTime =   dateFormat.format( releaseResultDate );
        map.put("ReleaseResultTime",releaseResultTime);
        String place = meeting.getPlace();
        map.put("Place",place);
        ArrayList<String>stringtopics = new ArrayList<>();

        for(Topic topic:topics){
            stringtopics.add(topic.getTopicname());
        }
        map.put("topic",stringtopics);
        Map<String,Map>returnMap = new HashMap<>();
        returnMap.put("veryDetailContactData",map);
        return returnMap;
    }

    public Map<String,String> judgeRoleInMeeting(JudgeRoleInMeetingRequest request){
        String fullname = request.getFullname();
        String username = request.getUsername();
        Map<String,String> returnMap = new HashMap<>();
        Meeting meeting = meetingRepository.findByFullname(fullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(fullname);
        }
        User chair = userRepository.findById(meeting.getChairId()).orElse(null);
        if(chair!=null) {
            if (chair.getUsername().equals(username)) {
                returnMap.put("userRoleInThisConference", "chair");
            } else
                returnMap.put("userRoleInThisConference", "not chair");
        }
        return returnMap;
    }

    //
    public Map<String,Set> essaysData(EssaysDataRequest request){
        String meetingFullname = request.getContactFullName();
        int contactState = request.getContactState();
        Meeting meeting = meetingRepository.findByFullname(meetingFullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(meetingFullname);
        }
        //会议的所有稿件
        Set<Paper> papers = meeting.getPapers();
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> returnSet;
        if(contactState>=3){
            returnSet = inSubmissionStage(papers);
        }
        else{
           returnSet =notInSubmissionStage(papers);
        }
        returnMap.put("respEssaysData",returnSet);
        return returnMap;
    }
    public Set<Map>notInSubmissionStage(Set<Paper>papers){
        Set<Map> returnSet = new HashSet<>();
        for(Paper paper:papers){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("title",paper.getTitle());
            Set<Writer> writers = paper.getWritersOfPaper();
            String[] writernames = new String[writers.size()];
            int i = 0;
            for(Writer writer:writers){
                writernames[i] = writer.getWritername();
                i++;
            }
            tempMap.put("writer",writernames);
            Set<Topic> topics = paper.getTopicsOfPaper();
            String[] topicnames = new String[topics.size()];
            i = 0;
            for(Topic topic:topics){
                topicnames[i] = topic.getTopicname();
                i++;
            }
            tempMap.put("topic",topicnames);
            tempMap.put("author",paper.getAuthorname());
            returnSet.add(tempMap);
        }
        return returnSet;
    }
    public int setEssayStatus(int reviewStatus,int firstModification,int secondModification,int rebuttal){
        if(reviewStatus==0 && firstModification==0 && secondModification==0){

            return 0;
        }
        if(reviewStatus==1 && firstModification==0 && secondModification==0){
         return 1;

        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0 &&rebuttal==-1){
          return 3;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0 &&rebuttal==0){
           return 4;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0 && rebuttal==1){
            return 5;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0){
            return 2;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==1){
            return 6;
        }
        return -1;
    }
    public Set<Map> inSubmissionStage(Set<Paper>papers){
        Set<Map> returnSet = new HashSet<>();
        for(Paper paper:papers){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("title",paper.getTitle());
            Set<Writer> writers = paper.getWritersOfPaper();
            String[] writernames = new String[writers.size()];
            int i = 0;
            for(Writer writer:writers){
                writernames[i] = writer.getWritername();
                i++;
            }
            tempMap.put("writer",writernames);
            Set<Topic> topics = paper.getTopicsOfPaper();
            String[] topicnames = new String[topics.size()];
            i = 0;
            for(Topic topic:topics){
                topicnames[i] = topic.getTopicname();
                i++;
            }
            tempMap.put("topic",topicnames);
            tempMap.put("author",paper.getAuthorname());
            Set<Map> reviewSet = new HashSet<>();
            Set<ReviewRelation> reviewRelations = reviewRelationRepository.findByPaperTitle(paper.getTitle());
            int leastState = 6;
            for(ReviewRelation reviewRelation:reviewRelations){
                Map<String,Object> reviewMap = new HashMap<>();
                reviewMap.put("name",reviewRelation.getPCmemberUsername());
                int reviewStatus = reviewRelation.getReviewStatus();
                int firstModification = reviewRelation.getFirstModification();
                int secondModification = reviewRelation.getSecondModification();
                int rebuttal = paper.getRebuttal();
                int essayState = setEssayStatus(reviewStatus,firstModification,secondModification,rebuttal);
                //拆分， essayState
                reviewMap.put("essayState",essayState);
                if(essayState<6&&leastState>essayState)
                    leastState = essayState;
                reviewSet.add(reviewMap);

            }
            tempMap.put("assignment",reviewSet);
            tempMap.put("state",leastState);
            returnSet.add(tempMap);
        }
        return  returnSet;
    }

    public int releaseResult(ContactInformationRequest request){
        String contactFullname = request.getContactFullName();
        Date rebuttalDeadline = request.getRebuttalDeadline();
        Meeting meeting = meetingRepository.findByFullname(contactFullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(contactFullname);
        }
        meeting.setMeetingState(4);
        meeting.setRebuttalDeadline(rebuttalDeadline);
        meetingRepository.save(meeting);
        //确定每篇论文的录用状态
        Set<Paper> papers = meeting.getPapers();
        if(papers ==null)
            return 1;
        for(Paper paper:papers){
            String title = paper.getTitle();

            Set<ReviewRelation> reviewRelations = reviewRelationRepository.findByPaperTitle(title);
            boolean ifPass = true;
            for(ReviewRelation reviewRelation:reviewRelations){
                if(reviewRelation.getScore()==-1 || reviewRelation.getScore()==-2){
                    ifPass = false;
                    break;
                }
            }
            if(ifPass) {
                paper.setRebuttal(-1);
                logger.debug("被驳回");
            }else {
                paper.setRebuttal(0);
                logger.debug("审核通过");
            }

        }
        for(Paper paper:papers){
            paperRepository.save(paper);
        }
        return 1;
    }

    public int releaseSecondResult(ContactInformationRequest request){
        String contactFullname = request.getContactFullName();
        Meeting meeting = meetingRepository.findByFullname(contactFullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(contactFullname);
        }
        meeting.setMeetingState(6);
        meetingRepository.save(meeting);
        return 1;
    }

    public int endRebuttal(ContactInformationRequest request){
        String contactFullname = request.getContactFullName();
        Meeting meeting = meetingRepository.findByFullname(contactFullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(contactFullname);
        }
        Date date = new Date();
        if(date.before(meeting.getRebuttalDeadline())){
            logger.debug("Can't end rebuttal!! before rebuttalDeadline!!!");
            throw new BeforeRebuttalTimeException(meeting.getFullname());
        }
        meeting.setMeetingState(5);
        meetingRepository.save(meeting);
        return 1;
    }
}