package com.test.jabis.domain.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("회원 가입 처리")
    public void signupTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/szs/signup")
                        .param("userId", "hhhhhongse@naver.com")
                        .param("password", "password")
                        .param("name", "홍길동"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

    }

}