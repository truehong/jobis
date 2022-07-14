package com.test.jabis.domain.token;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class TokenService {
    public static final String HEADER_AUTH = "Authorization";

    private static final String SALT = "default-salt";

    private Long expiredTime = 1000 * 60l * 60l;

    public <T> String generateToken(Map<String, String> options, String subject) {

        Date now = new Date();

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setIssuedAt(now)
                .setSubject(subject);

        for (String key : options.keySet()) {
            builder.claim(key, options.get(key));
        }

        String expiration = options.get("expiration");

        if(Strings.isNotEmpty(expiration)) {
            Date validity = new Date(now.getTime()
                    + Long.valueOf(expiration));
            builder.setExpiration(validity);
        }

        String jwt = builder.signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if(log.isInfoEnabled()){
                e.printStackTrace();
            }else{
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }
        return key;
    }

    public void verifyToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(this.generateKey())
                .parseClaimsJws(token);

        if (claimsJws == null) throw new RuntimeException();

       log.info("body = {}", claimsJws.getBody());
       log.info("header = {}", claimsJws.getHeader());
       log.info("signature = {}" , claimsJws.getSignature());
    }
}
