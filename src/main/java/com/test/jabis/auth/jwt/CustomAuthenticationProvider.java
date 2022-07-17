package com.test.jabis.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // todo: config
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            return null;
        }
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails
        ,authentication.getCredentials(), userDetails.getAuthorities());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
            return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
