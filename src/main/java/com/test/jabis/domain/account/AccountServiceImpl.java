package com.test.jabis.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    @Override
    public Account createAccount(SignupRequest signupRequest) {
        Account account = Account.create(signupRequest);
        Account newAccount = accountRepository.save(account);
        return newAccount;
    }
}
