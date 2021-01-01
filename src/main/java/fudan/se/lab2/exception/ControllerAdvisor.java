package fudan.se.lab2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * You can handle all of your exceptions here.
 *
 * @author LBW
 */
@ControllerAdvice//这个注解指这个类是处理其他controller抛出的异常
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    //当controller中抛出用户名不存在异常时会转到这个方法中处理
    @ExceptionHandler(fudan.se.lab2.exception.UsernameNotFoundException.class)
    ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.debug("not found error");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //会议名称已被注册
    @ExceptionHandler(MeetingnameHasBeenRegisteredException.class)
    ResponseEntity<?> handlerMeetingnameHasBeenRegisteredException(MeetingnameHasBeenRegisteredException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //用户名已被注册
    @ExceptionHandler(UsernameHasBeenRegisteredException.class)
    ResponseEntity<?> handlerUsernameHasBeenRegisteredException(UsernameHasBeenRegisteredException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
// 用户名密码不匹配
    @ExceptionHandler(fudan.se.lab2.exception.BadCredentialsException.class)
    ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
//无法通过简称搜索此会议
    @ExceptionHandler(MeetingnameNotFoundException.class)
    ResponseEntity<?> handleMeetingShortnameNotFoundException(MeetingnameNotFoundException ex) {
        logger.debug("meeting not found error");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
//会议中不包含此审核员
    @ExceptionHandler(MeetingDoseNotContainsPCmemberException.class)
    ResponseEntity<?> handleMeetingDoesNotContainsPCmember(MeetingDoseNotContainsPCmemberException ex) {
        logger.debug("meeting does not contains PCmember error");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //用户没有待处理的会议申请
    @ExceptionHandler(UserWithNoMeetingInlineException.class)
    ResponseEntity<?> handleUserWithNoMeetingInline( UserWithNoMeetingInlineException ex) {
        logger.debug("user has no meeting waiting for reviewing");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserWithNoInvitationException.class)
    ResponseEntity<?> handleUserWithNoInvitationException( UserWithNoInvitationException ex) {
        logger.debug("user dosen't has any received invitation");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //用户在当前会议下没有论文
    @ExceptionHandler(NoPaperException.class)
    ResponseEntity<?> handleNoPaperException( NoPaperException ex) {
        logger.debug("user dosen't has any paper in this meeting");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ReviewRelationNotFoundException.class)
    ResponseEntity<?> handleReviewRelationNotFoundException( ReviewRelationNotFoundException ex) {
        logger.debug("reviewRelation Not FOUND!");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(fudan.se.lab2.exception.PaperNotFoundException.class)
    ResponseEntity<?> handlePaperNotFoundException(PaperNotFoundException ex) {
        logger.debug("paper not found error");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OutOfTimeException.class)
    ResponseEntity<?> handleOutOfTimeException( OutOfTimeException ex) {
        logger.debug("Time sequence problem or out off date");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);//409
    }
    //不在论文提交时间内
    @ExceptionHandler(NotInSubmissionStageException.class)
    ResponseEntity<?> handleNotInSubmissionStageException( NotInSubmissionStageException ex) {
        logger.debug("Meeting is not in the submission stage!!!");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);//409
    }
    //不在第一次讨论时间内
    @ExceptionHandler(NotInFirstDiscussionStageException.class)
    ResponseEntity<?> handleNotInNotInFirstDiscussionStageException(NotInFirstDiscussionStageException ex) {
        logger.debug("Meeting is not in the first discussion stage!!!");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);//409
    }

    //不在第一次讨论时间内
    @ExceptionHandler(NotInSecondDiscussionStageException.class)
    ResponseEntity<?> handleNotInSecondDiscussionStageException(NotInSecondDiscussionStageException ex) {
        logger.debug("Meeting is not in the second discussion stage!!!");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);//409
    }

    //PCmember 不足
    @ExceptionHandler(PCmemberNotEnoughException.class)
    ResponseEntity<?> handlePCmemberNotEnoughException( PCmemberNotEnoughException ex) {
        logger.debug("PCmember not enough!!!");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);//406
    }
    //论文名已被注册
    @ExceptionHandler(TitleAlreadyExistException.class)
    ResponseEntity<?> handlerTitleAlreadyExistException( TitleAlreadyExistException ex) {
        logger.debug("handle title already exist");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    //不能提前结束rebuttal
    @ExceptionHandler(BeforeRebuttalTimeException.class)
    ResponseEntity<?> handleBeforeRebuttalTimeException( BeforeRebuttalTimeException ex) {
        logger.debug("can't end rebuttal now!");
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//400
    }

}
