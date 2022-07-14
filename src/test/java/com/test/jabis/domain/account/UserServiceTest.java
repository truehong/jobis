package com.test.jabis.domain.account;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("유저정보조회 테스트")
    public void getUserInfoTest() throws Exception {
        SignupRequest signupRequest =  SignupRequest.builder()
                .name("홍길동")
                .password("password")
                .regNo("860824-1655068")
                .userId("hhhhhongse@naver.com")
                .build();
        accountService.createAccount(signupRequest);
        String token = "Bearer " + accountService.login("hhhhhongse@naver.com", "password");
        System.out.println("created token = " + token);
        mockMvc.perform(MockMvcRequestBuilders.get("/szs/me")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                // todo json path
                .andDo(print())
                .andExpect(unauthenticated());
    }
}
