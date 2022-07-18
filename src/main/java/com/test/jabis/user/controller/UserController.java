package com.test.jabis.user.controller;

import com.test.jabis.auth.annotations.AuthCheck;
import com.test.jabis.common.dto.CommonResponse;
import com.test.jabis.user.annotation.UserInfo;
import com.test.jabis.user.dao.User;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.dto.UserInfoResponse;
import com.test.jabis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/szs/signup")
    public void signup(@Validated @RequestBody SignupRequest signupRequest) {
        userService.createAccount(signupRequest);
    }

    @AuthCheck
    @GetMapping("/szs/me")
    CommonResponse<UserInfoResponse> getUserInfo(@UserInfo User userDetail) throws AuthenticationException {
        UserInfoResponse user = userService.getUserInfo(userDetail.getUserId());
        if (user == null) throw new AuthenticationException();
        return CommonResponse.successOf(user, "내정보");
    }
}
