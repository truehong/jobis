package com.test.jabis.user.controller;

import com.test.jabis.common.annotations.AuthCheck;
import com.test.jabis.common.dto.CommonResponse;
import com.test.jabis.common.dto.ScrapResponse;
import com.test.jabis.user.service.UserService;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/szs/signup")
    public void signup(@Validated SignupRequest signupRequest) {
        userService.createAccount(signupRequest);
    }

    @AuthCheck
    @GetMapping("/szs/me")
    CommonResponse<UserInfoResponse> getUserInfo(HttpServletRequest servletRequest) {
        UserInfoResponse response = userService.getUserInfo();
        return CommonResponse.successOf(response, "내정보");
    }
}
