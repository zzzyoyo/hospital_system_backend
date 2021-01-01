package fudan.se.lab2.exception;

public class PaperNotFoundException  extends  RuntimeException{

    private static final long serialVersionUID = -6074753940710869977L;
    public PaperNotFoundException(String title){
        super("paper "+title+" doesn't exist!!");
    }

}
