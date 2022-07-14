package com.test.jabis.domain.user;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public UserInfoResponse getUserInfo() {
        return new UserInfoResponse();
    }
}
