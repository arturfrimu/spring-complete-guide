package com.arturfrimu.jwt.properties;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.security.jwt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtProperties {
    String secretKey;
    long expiration;
    RefreshToken refreshToken = new RefreshToken();

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RefreshToken {
        long expiration;
    }
}
