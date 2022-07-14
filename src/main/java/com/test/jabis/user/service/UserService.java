package com.test.jabis.user.service;

import com.test.jabis.user.dao.User;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.dto.UserInfoResponse;

public interface UserService {
    User createAccount(SignupRequest signupRequest);
    UserInfoResponse getUserInfo();
}
