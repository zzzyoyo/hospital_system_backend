package fudan.se.lab2.controller.request;

public class PassOrRefuseRequest {
    private String fullname;
    private String username;
    private String adminName;

    public PassOrRefuseRequest(){}
    public PassOrRefuseRequest(String fullname,String adminName,String username){
        this.fullname = fullname;
        this.adminName = adminName;
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
