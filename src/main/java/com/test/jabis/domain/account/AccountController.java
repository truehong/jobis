package com.test.jabis.domain.account;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    AccountService accountService;
    @PostMapping("/szs/signup")
    public void signup(@Validated SignupRequest signupRequest){
        accountService.createAccount(signupRequest);

    }
}
