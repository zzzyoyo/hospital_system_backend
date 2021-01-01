package fudan.se.lab2.controller.request;

public class AdminRequest {
    private String adminName;
    public AdminRequest(){}
    public AdminRequest(String adminName){
        this.adminName = adminName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
