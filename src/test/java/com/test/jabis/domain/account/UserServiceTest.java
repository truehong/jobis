package com.test.jabis.domain.account;

import com.test.jabis.user.dao.UserRepository;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class UserServiceTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저정보조회 테스트")
    public void getUserInfoTest() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .name("홍길동")
                .password("password")
                .regNo("860824-1655068")
                .userId("hhhhhongse@naver.com")
                .build();
        userService.createAccount(signupRequest);
        String token = "Bearer " + "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjU3OTk3ODE1MDUxLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTc5OTc4MTUsImV4cCI6MTY1ODAwMTQxNSwic3ViIjoiaGhoaGhvbmdzZUBuYXZlci5jb20iLCJwYXNzd29yZCI6IiQyYSQxMCQ4YWE4SG0zTXZjcXBORlZmaDVnYmVldTNCTU1oSVZGWERsOUZaSU1vUm9QMUMzRHhzVmdRaSIsInVzZXJfbmFtZSI6Iu2Zjeq4uOuPmSIsInVzZXJfaWQiOiJoaGhoaG9uZ3NlQG5hdmVyLmNvbSIsInJlZ19ubyI6IjAwMDAwMDAwIn0.YwO3bH4JodgmkAw8SKRRicau_ZS1H7YdcMnTkJAVu8Q";
        System.out.println("created token = " + token);
        mockMvc.perform(MockMvcRequestBuilders.get("/szs/me")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                // todo json path
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("유저정보조회 테스트 - 실패")
    public void getUserInfoTokenFailTest() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .name("홍길동")
                .password("password")
                .regNo("860824-1655068")
                .userId("hhhhhongse@naver.com")
                .build();
        userService.createAccount(signupRequest);
        String token = "Bearer " + "";
        System.out.println("created token = " + token);
        mockMvc.perform(MockMvcRequestBuilders.get("/szs/me")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                // todo json path
                .andDo(print())
                .andExpect(unauthenticated());
    }


}
