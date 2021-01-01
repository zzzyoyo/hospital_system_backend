package fudan.se.lab2.exception;

public class MeetingDoseNotContainsPCmemberException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;
    public MeetingDoseNotContainsPCmemberException(String PCmemberShortname, String meetingShortname){
        super(PCmemberShortname +" is not a PCmember of meeting "+ meetingShortname);
    }
}
