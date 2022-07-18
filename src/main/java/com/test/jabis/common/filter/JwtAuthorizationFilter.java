package com.test.jabis.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.jabis.auth.dto.Token;
import com.test.jabis.auth.handler.LoginFailHandler;
import com.test.jabis.auth.handler.LoginSuccessHandler;
import com.test.jabis.auth.jwt.JwtTokenProvider;
import com.test.jabis.common.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.test.jabis.auth.dto.Token.create;
import static com.test.jabis.common.dto.CommonResponse.successOf;

@Slf4j
@Component
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                                  LoginSuccessHandler loginSuccessHandler, LoginFailHandler loginFailHandler,
                                  ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailHandler = loginFailHandler;
        this.objectMapper = objectMapper;
        super.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/szs/login");
        setAuthenticationSuccessHandler(loginSuccessHandler);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("UsernamePasswordAuthenticationFilter.attemptAuthentication");
        String userId = (String) request.getParameter("userId");
        String password = (String) request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.getWriter().write(getTokenResponse(authResult));
        super.successfulAuthentication(request, response, chain, authResult);
    }

    private String getTokenResponse(Authentication authentication) throws IOException {
        Token token = create(jwtTokenProvider.generateToken(authentication));
        CommonResponse commonResponse = successOf(token, "로그인");
        return objectMapper.writeValueAsString(commonResponse);

    }
}
