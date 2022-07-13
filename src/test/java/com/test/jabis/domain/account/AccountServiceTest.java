package com.test.jabis.domain.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                        .param("email", "hhhhhongse@naver.com")
                        .param("password", "password")
                        .param("name", "홍길동"))
                .andExpect(status().isOk());

    }

}