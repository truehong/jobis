package com.test.jabis.auth.service;

import com.test.jabis.user.dao.User;
import com.test.jabis.user.dao.UserRepository;
import com.test.jabis.auth.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @Override
    public String login(String userId, String password) {
//        String encodedPassword = passwordEncoder.encode(password);
//        log.info("{} password encoded : {} " ,password ,encodedPassword);
        User user = userRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(() -> {
                    throw new RuntimeException();
                });
        String token = this.createToken(user);
        log.info("token = {} ", token);
        return token;
    }

    private String createToken(User user) {
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("user_id", user.getUserId());
        map.put("reg_no", user.getRegNo());
        return tokenService.generateToken(map, "user_info");
    }
}
