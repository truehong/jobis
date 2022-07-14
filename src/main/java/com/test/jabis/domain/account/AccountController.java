package com.test.jabis.domain.account;

import com.test.jabis.common.dto.CommonResponse;
import com.test.jabis.domain.token.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/szs/signup")
    public void signup(@Validated SignupRequest signupRequest) {
        accountService.createAccount(signupRequest);
    }

    @PostMapping("/szs/login")
    public CommonResponse<TokenResponse> login(HttpServletRequest request) {
        String password = request.getParameter("password");
        String userId = request.getParameter("userId");

        String createdToken = accountService.login(userId, password);
        TokenResponse response = TokenResponse.create(createdToken);
        return CommonResponse.successOf(response, "내정보");
    }
}
