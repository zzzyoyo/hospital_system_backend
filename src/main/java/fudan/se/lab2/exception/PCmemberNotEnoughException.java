package fudan.se.lab2.exception;

public class PCmemberNotEnoughException extends  RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public PCmemberNotEnoughException(String meetingFullname){
        super("PC members of meeting "+meetingFullname+"is not enough!(probably because some of the PC member is author or writer");
    }

}
