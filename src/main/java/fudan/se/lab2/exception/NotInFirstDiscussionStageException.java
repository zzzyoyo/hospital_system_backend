package fudan.se.lab2.exception;

public class NotInFirstDiscussionStageException extends  RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;
    public NotInFirstDiscussionStageException(String meetingFullname){
        super("Meeting "+meetingFullname+" is not in the first discssion stage!!!");
    }
}
