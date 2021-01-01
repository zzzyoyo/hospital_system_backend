package fudan.se.lab2.exception;

public class TitleAlreadyExistException  extends  RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public TitleAlreadyExistException(String title){
        super("title "+title+" has been used!!");
    }

}
