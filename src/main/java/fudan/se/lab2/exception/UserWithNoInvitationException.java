package fudan.se.lab2.exception;

public class UserWithNoInvitationException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public UserWithNoInvitationException(String username) {
        super("Username '" + username + "dosen't has any received invitation");
    }
}
