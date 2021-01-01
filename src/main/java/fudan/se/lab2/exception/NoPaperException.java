package fudan.se.lab2.exception;

public class NoPaperException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public NoPaperException(String authorname){
        super("user "+authorname+" hasn't submitted any paper to this meeting!");
    }
}
