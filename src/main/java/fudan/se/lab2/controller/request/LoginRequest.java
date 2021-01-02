package fudan.se.lab2.controller.request;


public class LoginRequest {
    private String username;
    private String password;
    private String role;

    public LoginRequest() {}
    public LoginRequest(String username){
        this.username = username;

    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String identity) {
        this.role = identity;
    }
}
