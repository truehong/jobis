package com.test.jabis.domain.account;

public interface AccountService {
    Account createAccount(SignupRequest signupRequest);

    void login(String userId, String password);
}
