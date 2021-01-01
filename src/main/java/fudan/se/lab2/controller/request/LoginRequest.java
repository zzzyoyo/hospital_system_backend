package fudan.se.lab2.controller.request;

/**
 * @author LBW
 */
public class LoginRequest {
    private String username;
    private String password;
    private String identity;

    public LoginRequest() {}
    public LoginRequest(String username){
        this.username = username;

    }

     public void fixedIdentity(){
         this.identity = "user";
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
