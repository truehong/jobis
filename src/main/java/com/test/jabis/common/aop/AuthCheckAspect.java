package com.test.jabis.common.aop;

import com.test.jabis.common.annotations.AuthCheck;
import com.test.jabis.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthCheckAspect {

    private final TokenService tokenService;
    final String EMPTY_SPACE = " ";
    final Integer TOKEN_VALUE_INDEX = 1;

    @Pointcut(" @annotation(annotation) ")
    private void pointcut(AuthCheck annotation) {
    }

    @Before("pointcut(annotation) ")
    public void checkToken(AuthCheck annotation) {
        String token = this.getToken();
        if (Strings.isEmpty(token)) throw new RuntimeException();
        if (!token.startsWith("Bearer")) throw new RuntimeException();

        String[] tokenInfo = token.split(EMPTY_SPACE);
        if(tokenInfo.length != 2) throw new RuntimeException();
        String tokenValue = tokenInfo[TOKEN_VALUE_INDEX];

        tokenService.verifyToken(tokenValue);
    }

    private String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(TokenService.HEADER_AUTH);
    }

}
