package com.test.jabis.user.service;

import com.test.jabis.user.dao.User;
import com.test.jabis.user.dao.UserRepository;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public com.test.jabis.user.dao.User createAccount(SignupRequest signupRequest) {
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        log.info("{} password encoded : {} ", signupRequest.getPassword(), encodedPassword);
        signupRequest.setPassword(encodedPassword);
        User user = User.create(signupRequest);
        log.info("user info = {} ", user.toString());
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public UserInfoResponse getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found - " + userId));
        return new UserInfoResponse(user.getRegNo(), user.getUserId(), user.getName());
    }
}
