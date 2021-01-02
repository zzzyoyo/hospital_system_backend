package fudan.se.lab2.domain;

import org.springframework.security.core.userdetails.UserDetails;

public interface Medical_personnel  {
    public String getUsername();

    public String getPassword() ;

    public void setPassword(String password);

    public void setUsername(String name);

    public int getIdentity();

}
