package fudan.se.lab2.controller.request;



/**
 * @author LBW
 */
public class RegisterRequest {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String[] sector;
    private String[] country;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String fullname, String email,String[] sector,String[] country) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.sector = sector;
        this.country = country;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getSector() {
        return sector;
    }

    public void setSector(String[] sector) {
        this.sector = sector;
    }

    public String[] getCountry() {
        return country;
    }

    public void setCountry(String[] country) {
        this.country = country;
    }
}

