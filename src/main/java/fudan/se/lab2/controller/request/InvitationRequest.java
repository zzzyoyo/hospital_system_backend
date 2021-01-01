package fudan.se.lab2.controller.request;

public class InvitationRequest {
    private String inviter;
    private String contactName;
    private String[] toInvite;

    public InvitationRequest(){}
    public InvitationRequest(String inviter,String contactName,String[] toInvite){
        this.toInvite = toInvite;
        this.contactName = contactName;
        this.inviter = inviter;
    }

    public String getInviter() {
        return inviter;
    }

    public String getContactName() {
        return contactName;
    }

    public String[] getToInvite() {
        return toInvite;
    }

    public void setToInvite(String[] toInvite) {
        this.toInvite = toInvite;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
