package fudan.se.lab2.exception;

public class OutOfTimeException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public OutOfTimeException(String meetingFullname){
        super("Meeting "+meetingFullname+" is out of time!!!");
    }

}
