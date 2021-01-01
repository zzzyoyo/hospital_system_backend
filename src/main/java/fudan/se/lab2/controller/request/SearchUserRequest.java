package fudan.se.lab2.controller.request;

public class SearchUserRequest {
    private String fullname;

    public SearchUserRequest(){}
    public SearchUserRequest(String fullname){
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
