package fudan.se.lab2.controller.request;

public class StartReviewRequest {
    private String contactName;
    private int way;

   public StartReviewRequest(){}
    public StartReviewRequest(String contactName,int way){
        this.contactName = contactName;
        this.way = way;
    }

    public String getContactName() {
        return contactName;
    }

    public int getWay() {
        return way;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setWay(int way) {
        this.way = way;
    }
}
