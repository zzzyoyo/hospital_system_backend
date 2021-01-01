package fudan.se.lab2.service;
import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.*;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;

import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.controller.request.RegisterRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
;

/**
 * @author LBW
 * 估计在此文件判断
 * register: 用户名是否与已注册的用户冲突
 * login: 实现用户登录的功能，让用户输入自己的账号和密码进行登录操作，
 * 需要保证只有使用正确的账号信息才能登入系统（该账户已经在系统
 * 内进行过注册，并且账户名与密码相匹配），在用户登录失败时在登
 * 录界面给其必要的提示，使用拦截器进行登录检查，即只有在用户进
 *  行登录后才能使用系统的功能
 *
 *
 */
@Service
public class AuthService {
    private UserRepository userRepository;
    private AdministratorRepository administratorRepository;
    private MeetingRepository meetingRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;
    private TopicRepository topicRepository;
    private ReviewRelationRepository reviewRelationRepository;
    private PCmemberToTopicRepository pCmemberToTopicRepository;
    private RebuttalRepository rebuttalRepository;
    private FirstDiscussionRepository firstDiscussionRepository;
    private SecondDiscussionRepository secondDiscussionRepository;
    Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    //public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository,MeetingRepository
    public AuthService(UserRepository userRepository, AdministratorRepository administratorRepository,MeetingRepository meetingRepository,InvitationRepository invitationRepository,PaperRepository paperRepository,TopicRepository topicRepository,ReviewRelationRepository reviewRelationRepository,PCmemberToTopicRepository pCmemberToTopicRepository,RebuttalRepository rebuttalRepository,FirstDiscussionRepository firstDiscussionRepository,SecondDiscussionRepository secondDiscussionRepository) {
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;
        this.topicRepository = topicRepository;
        this.reviewRelationRepository = reviewRelationRepository;
        this.pCmemberToTopicRepository = pCmemberToTopicRepository;
        this.rebuttalRepository = rebuttalRepository;
        this.firstDiscussionRepository = firstDiscussionRepository;
        this.secondDiscussionRepository = secondDiscussionRepository;
    }
//
    public User register(RegisterRequest request) {

          logger.debug("register start!!!");
        String userName = request.getUsername();

        if(userRepository.findByUsername(userName)!= null){
            logger.debug("username has been used!!");
             throw new UsernameHasBeenRegisteredException(userName);
        }

       /** 此次lab注册不需要考虑权限
        * lab3:在注册的情况下没有权限一说
        *
        *  for(String str:request.getAuthorities()){
            authoritySet.add(new Authority(str));
        }*/
        // //根据Register的信息新建

        User registerUser = new User(userName,request.getPassword(),request.getFullname(),request.getEmail(),request.getSector()[1],request.getCountry()[1]);
       //录入数据库
        userRepository.save(registerUser);

        return registerUser;
    }

    public Map<String,String> login(LoginRequest loginRequest){

        /**问题：怎么样把用户名不存在和密码错误的信息传给前端？
         *正解： 通过throw UsernameHasBeenRegisteredException 给ControllerAdvisor 来处理
         */

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String identity = loginRequest.getIdentity();
        //在用户登录失败时在登录界面给其必要的提示，使用拦截器进行登录检查，
        if(identity.equals("user")){
            logger.debug("login start!!! username is "+username);
            User loginUser = userRepository.findByUsername(username);
            //用户不存在
            if(loginUser == null) {
                logger.debug("username dosen't exist/!");
                throw new UsernameNotFoundException(username);
             //密码错误
            }else if(!loginUser.getPassword().equals(password))
            { logger.debug("wrong password!!");
                throw new BadCredentialsException("wrong password");
            }
            else{
                logger.debug("Login success");
            }
            JwtConfigProperties jwtConfigProperties = new JwtConfigProperties();
            jwtConfigProperties.setValidity(18000000);
            jwtConfigProperties.setSecret("FdseFdse2020");
            //生成token，给AC，AC返回hashmap，塞进去，否则空指针
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtConfigProperties);
            String token =jwtTokenUtil.generateToken(loginUser);
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
            map.put("userDetails",loginRequest.getUsername());
            return map;
        }else{
            logger.debug("login start!!! admin name is "+username);
            Administrator loginUser = administratorRepository.findByName(username);
            if(loginUser == null) {
                logger.debug("admin dosen't exist/!");
                throw new UsernameNotFoundException(username);
            }else if(!loginUser.getPassword().equals(password))
            {   logger.debug("wrong password!!");
                throw new BadCredentialsException("wrong password");
            }
            else{
                logger.debug("admin login success");
                JwtConfigProperties jwtConfigProperties = new JwtConfigProperties();
                jwtConfigProperties.setValidity(18000000);
                jwtConfigProperties.setSecret("FdseFdse2020");
                //生成token，给AC，AC返回hashmap，塞进去，否则空指针
                JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtConfigProperties);
                User user = new User(loginUser.getName(),loginUser.getPassword(),null,loginUser.getEmail(),null,null);
                String token =jwtTokenUtil.generateToken(user);
                Map<String,String> map = new HashMap<>();
                map.put("token",token);
                map.put("userDetails",loginRequest.getUsername());
                return map;
            }
        }
    }

    public void cancellation(String username){
        User cancellationUser;
        if((cancellationUser =userRepository.findByUsername(username))!=null){
            userRepository.delete(cancellationUser);
            logger.debug("删除用户"+username);
        }
        Administrator administrator;
    if((administrator = administratorRepository.findByName(username))!= null){
        administratorRepository.delete(administrator);
        logger.debug("删除管理员"+username);
        }
    }


    //会议申请
    public int meeting(MeetingRequest request){

        /**
         * 测试下所有user和meeting1的关系
         */

        String fullname = request.getFullname();
        String shortname = request.getShortname();
        String chairname = request.getChairname();

       //meeting token 有问题，是login的token
        //同时查找待审核和以通过的meeting
        if(meetingRepository.findByFullname(fullname)!=null){
            logger.debug("fullname has been used!");
            throw new MeetingnameHasBeenRegisteredException(fullname);
        } else if(meetingRepository.findByShortname(shortname)!=null){
            logger.debug("shortname has been used!");
            throw new MeetingnameHasBeenRegisteredException(shortname);
        }
        User chair;
        if((chair=userRepository.findByUsername(chairname))==null){
            logger.debug("user doesn't exist. Please check the chair name");
            throw new UsernameNotFoundException(chairname);
        }
        if(request.getResultReleaseTime().before(request.getDeadline())){
            logger.debug("resultReleaseTime before Deadline!!");
            throw new OutOfTimeException(fullname);
        }
        if(request.getOrganizationTime().before(request.getResultReleaseTime())){
            logger.debug("orginazationTime before resultReleaseTime!!");
            throw new OutOfTimeException(fullname);
        }

        String place = request.getPlace()[1];

        Meeting meeting = new Meeting(shortname,fullname,request.getOrganizationTime(),place,request.getDeadline(),request.getResultReleaseTime());
        Long chairID = chair.getId();
        //先加多对一的多的一方

        meeting.setChairId(chairID);

       //先存多的一方
        logger.debug("try to save meeting");
        meetingRepository.save(meeting);


        Meeting alreadySave = meetingRepository.findByShortname(shortname);
        if(alreadySave==null){
            logger.debug("meeting hasn't been saved yet");
        }

        userRepository.save(chair);
        chair = userRepository.findByUsername(chairname);
        Set<Meeting> meetingsOfChair = chair.getMeetingAsChair();
        for(Meeting meetingOfChair:meetingsOfChair){
            logger.debug(meetingOfChair.getShortname());
        }

          logger.debug("meeting save finish!");
        //建立meeting-topic映射关系
        String[] topics = request.getTopic();
        //
        for(String topicname:topics){
            Topic tempTopic;
            if((tempTopic= topicRepository.findByTopicname(topicname))==null) {
                logger.debug("topicRepository try to save new Topic");
               tempTopic = new Topic(topicname);

            }
                //meeting是@ManyToMany的控制方（写了JoinTable），先加
                meeting.addTopics(tempTopic);
                //再加Topic
                tempTopic.addMeeting(meeting);

            topicRepository.save(tempTopic);


        }

        //meeting先存
        meetingRepository.save(meeting);

        logger.debug("Topics finish!!");


        return 1;
    }

    public Map<String,Map> personalInformation(PersonalInformationRequest request){
        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        if(user == null) {
            logger.debug("username doesn't exist/!");
            throw new UsernameNotFoundException(username);
        }
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("fullname",user.getFullname());
        map.put("email",user.getEmail());
        map.put("sector",user.getSector());
        map.put("country",user.getCountry());
        Map<String,Map> returnMap = new HashMap<>();
        returnMap.put("personalInformation",map);
        return returnMap;
    }

    public int getReviewStatus(int reviewStatus,int firstModification,int secondModification,String title,int rebuttal){
        if(reviewStatus==0 && firstModification==0 && secondModification==0){
            logger.debug("essay "+title+"  state : 0  未批阅");
           return 0;
        }
        if(reviewStatus==1 && firstModification==0 && secondModification==0){
            logger.debug("essay "+title+"  state :1 已批阅待首次确认");
           return 1;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0 && rebuttal==-1){
            logger.debug("essay "+title+"  state : 3");
           return 3;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0 && rebuttal==0){
            logger.debug("essay "+title+"  state : 4  已驳回待提交rebuttal");
          return 4;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0 && rebuttal==1){
            logger.debug("essay "+title+"  state : 5： 已提交rebuttal等待再次确认");
            return 5;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==0){
            logger.debug("essay "+title+"  state : 2 已首次确认");
            return 2;
        }
        if(reviewStatus==1 && firstModification==1 && secondModification==1){
            logger.debug("essay "+title+"  state : 6已再次确认");
           return 6;
        }
        return 0;
    }
    public Set<Map> addFirstDiscussion(String title){
        Set<Map> firstDiscussSet = new HashSet<>();
        Set<FirstDiscussion> firstDiscussions = firstDiscussionRepository.findByTitle(title);
        for(FirstDiscussion firstDiscussion:firstDiscussions){
            Map<String,String> discussMap = new HashMap<>();
            discussMap.put("speaker",firstDiscussion.getSpeaker());
            discussMap.put("content",firstDiscussion.getStatement());
            firstDiscussSet.add(discussMap);
        }
        return firstDiscussSet;
    }
    public Set<Map> addSecondDiscussion(String title){
        Set<Map> secondDiscussSet = new HashSet<>();
        Set<SecondDiscussion> secondDiscussions = secondDiscussionRepository.findByTitle(title);
        for(SecondDiscussion secondDiscussion:secondDiscussions){
            Map<String,String> discussMap = new HashMap<>();
            discussMap.put("speaker",secondDiscussion.getSpeaker());
            discussMap.put("content",secondDiscussion.getStatement());
           secondDiscussSet.add(discussMap);
        }
        return secondDiscussSet;
    }



    public Map<String ,Set> auditInformation(AuditRequest request){
        //username： 审稿人username
        String username = request.getUsername();
        String meetingFullname = request.getContactName();
        logger.debug(meetingFullname);
        Meeting meeting;
        User pcMember;
        if((meeting=meetingRepository.findByFullname(meetingFullname))==null){
            logger.debug("meeting dosen't exist !");
            throw new MeetingnameNotFoundException(meetingFullname);
        }
        if((pcMember = userRepository.findByUsername(username))==null){
            logger.debug("PCmember dosen't exist !");
            throw new UsernameNotFoundException(username);
        }
        Set<String> pcmembernames = new HashSet<>();
        for(User temp:meeting.getMeetingPCmembers()){
            pcmembernames.add(temp.getUsername());
        }
        if(!pcmembernames.contains(pcMember.getUsername())){
            logger.debug(username +" is not a PCmember of meeting"+meetingFullname);
            throw new MeetingDoseNotContainsPCmemberException(username,meetingFullname);
        }

        Set<ReviewRelation> reviewRelations = reviewRelationRepository.findByMeetingFullnameAndAndPCmemberUsername(meeting.getFullname(),pcMember.getUsername());
        Set<Map> returnSet = new HashSet<>();
        for(ReviewRelation reviewRelation:reviewRelations){

            Map<String,Object> tempMap = new HashMap<>();
            Paper paper = paperRepository.findByTitle(reviewRelation.getPaperTitle());
            int reviewStatus = reviewRelation.getReviewStatus();
            int firstModification = reviewRelation.getFirstModification();
            int secondModification = reviewRelation.getSecondModification();
            String title = paper.getTitle();
            int paperRebuttal = paper.getRebuttal();
            tempMap.put("name",paper.getAuthorname());
            tempMap.put("extract",paper.getSummary());
            tempMap.put("link",paper.getPath());
            tempMap.put("title",paper.getTitle());
            //增加讨论区数据

            tempMap.put("firstDiscussion",addFirstDiscussion(title));
            tempMap.put("secondDiscussion",addSecondDiscussion(title));

            Rebuttal rebuttal = rebuttalRepository.findByTitle(paper.getTitle());
            if(rebuttal==null)
                tempMap.put("rebuttal","");
            else
                tempMap.put("rebuttal",rebuttal.getRebuttalRequest());

            //下判断论文状态

            int status =getReviewStatus(reviewStatus,firstModification,secondModification,title,paperRebuttal);
            tempMap.put("essayState",status);
            returnSet.add(tempMap);
        }
        Map<String,Set> returnMap = new HashMap<>();
        returnMap.put("essayNeedHandle",returnSet);
        return returnMap;
    }

    public Map<String,Map> contactInformation(ContactInformationRequest request){
        String fullname = request.getContactFullName();
        Map<String,Object> map = new HashMap<>();
        if(meetingRepository.findByFullname(fullname)!=null){
            Meeting meeting = meetingRepository.findByFullname(fullname);
            ArrayList<String>stringtopics = new ArrayList<>();
            Set<Topic>topics = meeting.getTopicsOfMeeting();

            SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd ");

            for(Topic topic:topics){
                stringtopics.add(topic.getTopicname());
            }
            Date releaseResultDate = meeting.getResultReleaseTime();
            Date organizationDate = meeting.getOrganizationTime();
            Date deadlineDate = meeting.getDeadline();

            String releaseResultTime =   dateFormat.format( releaseResultDate );
            String organizationTime = dateFormat.format(organizationDate);
            String deadlineTime = dateFormat.format(deadlineDate);

            map.put("shortname",meeting.getShortname());
            map.put("fullname",meeting.getFullname());
            map.put("deadline",deadlineTime);
            map.put("resultReleaseTime",releaseResultTime);
            map.put("organizationTime",organizationTime);
            map.put("place",meeting.getPlace());
            map.put("state",meeting.getMeetingState());
            map.put("topic",stringtopics);
        } else{
            throw new MeetingnameNotFoundException(fullname);
        }
        Map<String,Map> returnMap = new HashMap<>();
        returnMap.put("contactInformation",map);
        return returnMap;
    }

    ////该用户所有参加了的会议，需要返回会议名和该用户在该会议的角色
    public Map<String,Set> personal(PersonalInformationRequest request){
        String username = request.getUsername();
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> set = new HashSet<>();

        User user = userRepository.findByUsername(username);
        if(user==null){
            logger.debug("user not found");
            throw new UsernameNotFoundException(username);
        }

        Set<Meeting> meetings = user.getMeetingAsChair();
        Set<Meeting> meetings1 = user.getMeetingAsAuthor();
        Set<Meeting> meetings2 = user.getMeetingAsPCmember();
        for(Meeting meeting:meetings1){
            if (!meetings.contains(meeting))
                meetings.add(meeting);
        }for(Meeting meeting:meetings2){
            if (!meetings.contains(meeting))
                meetings.add(meeting);
        }
        for(Meeting meeting:meetings){

            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("shortname",meeting.getShortname());
            tempMap.put("fullname",meeting.getFullname());
            ArrayList<String> roles = new ArrayList<>();
            if(user.getId().equals(meeting.getChairId()))
                roles.add("Chair");
            if(meeting.getMeetingAuthor().contains(user))
                roles.add("Author");
            if(meeting.getMeetingPCmembers().contains(user))
                roles.add("PCmember");
            tempMap.put("roles",roles);
            set.add(tempMap);
        }
        returnMap.put("respContactData",set);

        return returnMap;
    }
    //new

    public Map<String,Set> searchUser(SearchUserRequest request){
        String fullname = request.getFullname();
        logger.debug(fullname);
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> set = new HashSet<>();
        List<User> users = userRepository.findByFullname(fullname);
        //允许输入username
        User user1 = userRepository.findByUsername(fullname);
        if(user1!=null){
            users.add(user1);
        }

        for(User user:users){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("username",user.getUsername());

            tempMap.put("fullname",user.getFullname());
            tempMap.put("email",user.getEmail());
            tempMap.put("sector",user.getSector());
            tempMap.put("country",user.getCountry());
            set.add(tempMap);
        }
        returnMap.put("searchResults",set);
        return returnMap;
    }

    public Map<String,Set> admin(AdminRequest request){
        String adminName = request.getAdminName();
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> set = new HashSet<>();

        Administrator admin = administratorRepository.findByName(adminName);
        if(admin==null){
            logger.debug("user not found");
            throw new UsernameNotFoundException(adminName);
        }

        Set<Meeting> meetings = meetingRepository.findByMeetingState(0);

        for(Meeting meeting:meetings){
            Optional<User> chair = userRepository.findById(meeting.getChairId());

            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("shortname",meeting.getShortname());
            tempMap.put("fullname",meeting.getFullname());
            tempMap.put("deadline",meeting.getDeadline());
            tempMap.put("resultReleaseTime",meeting.getResultReleaseTime());
            tempMap.put("organizationTime",meeting.getOrganizationTime());
            tempMap.put("place",meeting.getPlace());
            chair.ifPresent(user -> tempMap.put("applicant", user.getUsername()));
            Set<Topic> topics = meeting.getTopicsOfMeeting();
            String[] topicnames = new String[topics.size()];
            int i = 0;
            for(Topic topic:topics){
                topicnames[i] = topic.getTopicname();
                i++;
            }
            tempMap.put("topic",topicnames);
            set.add(tempMap);
        }
        returnMap.put("respContactData",set);

        return returnMap;
    }

    public int passContact(PassOrRefuseRequest request){

        String username = request.getUsername();
        String fullname = request.getFullname();
        String adminName = request.getAdminName();
        logger.debug("usernme : "+username+" fullname "+fullname + " adminName "+adminName);
        Meeting meeting = meetingRepository.findByFullname(fullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(fullname);
        }
        Administrator admin = administratorRepository.findByName(adminName);
        Long adminID = admin.getId();
        meeting.setAdminId(adminID);
        meeting.setMeetingState(1);
        User chair = userRepository.findByUsername(username);

        if(chair ==null){
            throw new UsernameNotFoundException(username);
        }
        chair.addMeetingAsChair(meeting);
        meeting.setChair(chair);
        meeting.addMeetingPCmembers(chair);
        chair.addMeetingAsPCmember(meeting);


        meetingRepository.save(meeting);
        Set<Topic> topics = meeting.getTopicsOfMeeting();
        for(Topic topic:topics){
            PCmemberToTopic pCmemberToTopic = new PCmemberToTopic(meeting.getFullname(),meeting.getChair().getUsername(),topic.getTopicname());
            pCmemberToTopicRepository.save(pCmemberToTopic);
        }
        return 1;
    }

    public int refuseContact(PassOrRefuseRequest request){
        String fullname = request.getFullname();
        String adminName = request.getAdminName();
        Meeting meeting = meetingRepository.findByFullname(fullname);
        if(meeting==null){
            logger.debug("meeting doesn't exist");
            throw new MeetingnameNotFoundException(fullname);
        }
        Administrator admin = administratorRepository.findByName(adminName);
        Long adminID = admin.getId();
        meeting.setAdminId(adminID);
        meeting.setMeetingState(-1);
        meetingRepository.save(meeting);
        return 1;
    }

    public Map<String ,Set> sendInvitation(InvitationRequest request){
        String inviter = request.getInviter();
        String fullname = request.getContactName();
        String[] toInvite = request.getToInvite();
        Meeting meeting = meetingRepository.findByFullname(fullname);
        String contactName = meeting.getShortname(); //简称
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> set = new HashSet<>();
        for(String toinviteUser:toInvite){
            Invitation invitation = new Invitation(inviter,toinviteUser,contactName);
            ArrayList<Invitation> temps= invitationRepository.findByMeetingShortname(contactName);
            boolean ifRepeat = false;
            for(Invitation temp:temps){
                if(temp.getPCmemberUsername().equals(toinviteUser)){
                    ifRepeat = true;
                    break;
                }
            }
            if(!ifRepeat)
                invitationRepository.save(invitation);
        }

        ArrayList<Invitation> invitations = invitationRepository.findByMeetingShortname(contactName);

        //chair
        Map<String,String> chairMap = new HashMap<>();
        User chair = meeting.getChair();
        chairMap.put("username",chair.getUsername());
        chairMap.put("fullname",chair.getFullname());
        chairMap.put("email",chair.getEmail());
        chairMap.put("country",chair.getCountry());
        chairMap.put("state","已同意");
        set.add(chairMap);
     //待确认和已拒绝
        for(Invitation tempInvitation:invitations){
            Map<String,String> tempMap = new HashMap<>();
            tempMap.put("username",tempInvitation.getPCmemberUsername());
            User tempUser = userRepository.findByUsername(tempInvitation.getPCmemberUsername());
            tempMap.put("fullname",tempUser.getFullname());
            tempMap.put("email",tempUser.getEmail());
            tempMap.put("country",tempUser.getCountry());
            if(tempInvitation.getState()==0)
                tempMap.put("state","待确认");
            else if(tempInvitation.getState()==-1)
                tempMap.put("state","已拒绝");
            else if(tempInvitation.getState()==1)
                tempMap.put("state","已同意");
            set.add(tempMap);
        }
        returnMap.put("respPCmembers",set);

        return returnMap;
    }

    public Map<String ,Set> PCmember(InvitationRequest request){
        String fullname = request.getContactName();
        Meeting meeting = meetingRepository.findByFullname(fullname);
        String contactName = meeting.getShortname(); //简称
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> set = new HashSet<>();

        ArrayList<Invitation> invitations = invitationRepository.findByMeetingShortname(contactName);

        //chair
        Map<String,String> chairMap = new HashMap<>();
        User chair = meeting.getChair();
        chairMap.put("username",chair.getUsername());
        chairMap.put("fullname",chair.getFullname());
        chairMap.put("email",chair.getEmail());
        chairMap.put("country",chair.getCountry());
        chairMap.put("state","已同意");
        set.add(chairMap);

//        //待确认和已拒绝
        for(Invitation tempInvitation:invitations){
            Map<String,String> tempMap = new HashMap<>();
            tempMap.put("username",tempInvitation.getPCmemberUsername());
            User tempUser = userRepository.findByUsername(tempInvitation.getPCmemberUsername());
            tempMap.put("fullname",tempUser.getFullname());
            tempMap.put("country",tempUser.getCountry());
            tempMap.put("email",tempUser.getEmail());
            if(tempInvitation.getState()==0)
                tempMap.put("state","待确认");
            else if(tempInvitation.getState()==1)
                tempMap.put("state","已同意");
            else if(tempInvitation.getState()==-1)
                tempMap.put("state","已拒绝");
            set.add(tempMap);
        }
        returnMap.put("respPCmembers",set);

        return returnMap;
    }

    /**
     *  myContactStateDelivered:[
     *   {
     *     contactState:'审核中',
     *     tableData: [{
     *       FullName:'第32届全国互联网顶尖人才大会',
     *       ShortName:'互联网大会',
     *       BeginTime:'2019-10-18',
     *       ContributeDDL:'2019-11-12',
     *       ReleaseResultTime:'2019-12-3',
     *       Place:'Asia,ShangHai'
     *     }]
     *   },
     * @param request
     * @return
     */
    public Map<String,Set> userContactStateInformation(UserContactStateRequest request){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        String username = request.getUsername();
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> set = new HashSet<>();

        User chair = userRepository.findByUsername(username);
        Set<Meeting> meetings = meetingRepository.findByChairId(chair.getId());
        for(Meeting meeting:meetings){
            Map<String,String> tempMap = new HashMap<>();
            if(meeting.getMeetingState()==-1)
                tempMap.put("contactState","已拒绝");
            else if(meeting.getMeetingState()==0)
                tempMap.put("contactState","审核中");
            else if(meeting.getMeetingState()==1 || meeting.getMeetingState()==2 || meeting.getMeetingState()==3 || meeting.getMeetingState()==4 || meeting.getMeetingState()==5 || meeting.getMeetingState()==6)
                tempMap.put("contactState","已通过");
            tempMap.put("FullName",meeting.getFullname());
            tempMap.put("ShortName",meeting.getShortname());
            tempMap.put("BeginTime",ft.format(meeting.getOrganizationTime()));
            tempMap.put("ContributeDDL",ft.format(meeting.getDeadline()));
            tempMap.put("ReleaseResultTime",ft.format(meeting.getResultReleaseTime()));
            tempMap.put("Place",meeting.getPlace());
            set.add(tempMap);
        }

        returnMap.put("myContactStateDelivered",set);
        return returnMap;
    }

    public Map<String,Set> invitationReceivedInformation(InvitationReceivedRequest request){
        String username = request.getUsername();
        Map<String,Set> returnMap = new HashMap<>();
        Set<Map> set = new HashSet<>();
        List<Invitation> invitations;
        if((invitations=invitationRepository.findByPCmemberUsername(username))==null){
            logger.debug("User "+username+" has no Received invitation !");
            throw new UserWithNoInvitationException(username);
        }
        for(Invitation invitation:invitations){
            if(invitation.getState()==0){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("chair",invitation.getChairUsername());
            Meeting meeting = meetingRepository.findByShortname(invitation.getMeetingShortname());
            tempMap.put("FullName",meeting.getFullname());
            logger.debug(meeting.getFullname());
            Set<Topic> topics = meeting.getTopicsOfMeeting();
            String[] topicnames = new String[topics.size()];
            int i = 0;
            for(Topic topic:topics){
                topicnames[i] = topic.getTopicname();
                i++;
            }
            tempMap.put("topic",topicnames);
            set.add(tempMap);
            }
        }
        returnMap.put("invitesData",set);
        return returnMap;
    }

    public int openMeeting(ContactInformationRequest request){
        logger.debug("here");
        String fullname = request.getContactFullName();
        logger.debug(fullname);
        Meeting meeting = meetingRepository.findByFullname(fullname);

        meeting.setMeetingState(2);
        meetingRepository.save(meeting);
       meeting.getMeetingState();
        return meeting.getMeetingState();
    }

    public int refuseInvitation(RefuseInvitationRequest request){
        String username = request.getUsername();
        String fullname = request.getFullname();
        boolean refutation = request.isRefutation();
        Meeting meeting = meetingRepository.findByFullname(fullname);
        ArrayList<Invitation> invitations = invitationRepository.findByMeetingShortname(meeting.getShortname());
        Invitation invitation = new Invitation();
        for(Invitation tempInvitation:invitations){
            if(tempInvitation.getPCmemberUsername().equals(username))
                invitation = tempInvitation;
        }
        //拒绝
        if(refutation){
            invitation.setState(-1);
            invitationRepository.save(invitation);
        }
        else{
            String[] topicnames = request.getTopic();
            User user = userRepository.findByUsername(username);
            meeting.addMeetingPCmembers(user);
            user.addMeetingAsPCmember(meeting);
            meetingRepository.save(meeting);
            userRepository.save(user);
            for(String topicname:topicnames){
                if (pCmemberToTopicRepository.findByMeetingFullnameAndTopicnameAndAndUsername(meeting.getFullname(), topicname, username)==null) {
                    PCmemberToTopic pCmemberToTopic = new PCmemberToTopic(meeting.getFullname(),username,topicname);
                    pCmemberToTopicRepository.save(pCmemberToTopic);
                }
            }


            invitation.setState(1);
            invitationRepository.save(invitation);
        }
        return 1;
    }

}
