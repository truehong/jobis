package com.test.jabis.user.service;

import com.test.jabis.user.dao.User;
import com.test.jabis.user.dao.UserRepository;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User createAccount(SignupRequest signupRequest) {
        //todo: password 인코딩 결과값 다른 오류 확인
//        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
//        log.info("{} password encoded : {} " ,signupRequest.getPassword() ,encodedPassword);
//        signupRequest.setPassword(encodedPassword);

        User user = User.create(signupRequest);
        log.info("user info = {} ", user.toString());
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public UserInfoResponse getUserInfo() {
        return new UserInfoResponse();
    }
}
