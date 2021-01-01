package fudan.se.lab2.exception;

public class NotInSecondDiscussionStageException extends  RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public NotInSecondDiscussionStageException(String meetingFullname){
        super("Meeting "+meetingFullname+" is not in the second discssion stage!!!");
    }
}
