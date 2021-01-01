package fudan.se.lab2.controller.request;

public class RefuseInvitationRequest {
    private String username;
    private String fullname;
    private boolean refutation;
    private String[] topic;

    public RefuseInvitationRequest(){}
    public RefuseInvitationRequest(String username,String fullname,boolean refutation,String[] topic){
        this.fullname = fullname;
        this.username = username;
        this.refutation = refutation;
        this.topic = topic;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRefutation() {
        return refutation;
    }

    public void setRefutation(boolean refutation) {
        this.refutation = refutation;
    }

    public String[] getTopic() {
        return topic;
    }

    public void setTopic(String[] topic) {
        this.topic = topic;
    }
}
