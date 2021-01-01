package fudan.se.lab2.exception;

public class NotInSubmissionStageException  extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;

    public NotInSubmissionStageException(String meetingFullname){
        super("Meeting "+meetingFullname+" is not in the submission stage!!!");
    }
}
