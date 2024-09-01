package com.arturfrimu.jwt.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtProperties {
    private String secretKey;
    private long expiration;
    private RefreshToken refreshToken = new RefreshToken();

    @Setter
    @Getter
    public static class RefreshToken {
        private long expiration;
    }
}
