package fudan.se.lab2.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Some properties about JWT.
 * You can change the value in `application.properties`.
 *

 */
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtConfigProperties {
    //token有效期
    private int validity = 18000000;
    //加密密钥
    private String secret = "FdseFdse2020";

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
