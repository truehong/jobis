package com.test.jabis.domain.user;

import com.test.jabis.common.annotations.AuthCheck;
import com.test.jabis.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @AuthCheck
    @GetMapping("/szs/me")
    CommonResponse<UserInfoResponse> getUserInfo(HttpServletRequest servletRequest) {
        UserInfoResponse response = userService.getUserInfo();
        return CommonResponse.successOf(response, "내정보");
    }
}
