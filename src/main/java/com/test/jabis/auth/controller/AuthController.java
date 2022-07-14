package com.test.jabis.auth.controller;

import com.test.jabis.auth.service.AuthService;
import com.test.jabis.common.dto.ScrapResponse;
import com.test.jabis.auth.jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService accountService;

    @PostMapping("/szs/login")
    public ScrapResponse<TokenResponse> login(HttpServletRequest request) {
        String password = request.getParameter("password");
        String userId = request.getParameter("userId");

        String createdToken = accountService.login(userId, password);
        TokenResponse response = TokenResponse.create(createdToken);
        //return ScrapResponse.successOf(response, "내정보");
        return null;
    }
}
