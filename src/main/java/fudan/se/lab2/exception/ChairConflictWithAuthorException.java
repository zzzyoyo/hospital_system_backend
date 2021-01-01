package fudan.se.lab2.exception;

public class ChairConflictWithAuthorException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;
    public ChairConflictWithAuthorException(String chairname){
        super(chairname+"try to submit paper to his Own meeting !");
    }
}
