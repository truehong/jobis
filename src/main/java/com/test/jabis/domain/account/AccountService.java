package com.test.jabis.domain.account;

public interface AccountService {
    Account createAccount(SignupRequest signupRequest);

    String login(String userId, String password);
}
