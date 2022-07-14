package com.test.jabis.domain.account;

import com.test.jabis.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @Override
    public Account createAccount(SignupRequest signupRequest) {
        //todo: password 인코딩 결과값 다른 오류 확인
//        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
//        log.info("{} password encoded : {} " ,signupRequest.getPassword() ,encodedPassword);
//        signupRequest.setPassword(encodedPassword);

        Account account = Account.create(signupRequest);
        log.info("user info = {} ", account.toString());
        Account newAccount = accountRepository.save(account);
        return newAccount;
    }

    @Override
    public String login(String userId, String password) {
//        String encodedPassword = passwordEncoder.encode(password);
//        log.info("{} password encoded : {} " ,password ,encodedPassword);
        Account account = accountRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(() -> {
                    throw new RuntimeException();
                });
        String token = this.createToken(account);
        log.info("token = {} ", token);
        return token;
    }

    private String createToken(Account account) {
        Map<String, String> map = new HashMap<>();
        map.put("name", account.getName());
        map.put("user_id", account.getUserId());
        map.put("reg_no", account.getRegNo());
        return tokenService.generateToken(map, "user_info");
    }
}
