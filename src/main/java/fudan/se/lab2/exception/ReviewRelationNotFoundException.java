package fudan.se.lab2.exception;

public class ReviewRelationNotFoundException extends  RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;

    public  ReviewRelationNotFoundException (String title,String PCmemberUsername) {
        super("Review Assignment of paper "+ title+" to PCmember "+PCmemberUsername +" not Found!!!");
    }
}
