package com.test.jabis.auth.jwt;

import com.test.jabis.user.dao.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    public static final String HEADER_AUTH = "Authorization";

    private static final String SALT = "default-salt";

    private Long expiredTime = 1000 * 60l * 60l;

    private final UserDetailsService userDetailsService;

    public <T> String generateToken(Authentication authResult) {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        User user = principalDetails.getUser();
        return getString(user);
    }

    public <T> String generateToken(User user) {
        return getString(user);
    }

    private String getString(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiredTime);
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setSubject(user.getUserId());
        builder.claim("user_name", user.getName())
                .claim("user_id", user.getUserId())
                .claim("reg_no", user.getRegNo());
        String jwt = builder.signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }


    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }
        return key;
    }

    public boolean verifyToken(String token) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(token);
        } catch (Exception jwtException) {
            log.error(jwtException.getMessage(), jwtException);
            return false;
        }
        return claimsJws != null;
    }

    public Authentication getAuthentication(String token) {
        final Claims claims = Jwts.
                parser()
                .setSigningKey(this.generateKey())
                .parseClaimsJws(token)
                .getBody();
        final String userId = claims.getSubject();
        final User user = User.create(claims);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails
                , userDetails.getPassword(), userDetails.getAuthorities());
        result.setDetails(user);
        return result;
    }


}
