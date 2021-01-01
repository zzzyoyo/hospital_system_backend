package fudan.se.lab2.exception;

public class BeforeRebuttalTimeException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public BeforeRebuttalTimeException(String meetingFullname){
        super("Meeting "+meetingFullname+" can't end rebuttal now!!!");
    }

}
