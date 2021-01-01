package fudan.se.lab2.exception;

public class UserWithNoMeetingInlineException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public UserWithNoMeetingInlineException(String username) {
        super("Username '" + username + "' dosen't has any MeetingInline");
    }

}
