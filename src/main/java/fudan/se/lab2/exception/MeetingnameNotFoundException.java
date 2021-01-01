package fudan.se.lab2.exception;

public class MeetingnameNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;

    public MeetingnameNotFoundException(String meetingname) {
        super("Name of the meeting '" + meetingname + "' does't exist！");
    }
}
