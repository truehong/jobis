package com.test.jabis.domain.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Account createAccount(SignupRequest signupRequest) {
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        signupRequest.setPassword(encodedPassword);

        Account account = Account.create(signupRequest);
        log.info("user info = " + account.toString());
        Account newAccount = accountRepository.save(account);
        return newAccount;
    }

    @Override
    public void login(String userId, String password) {
    }
}
