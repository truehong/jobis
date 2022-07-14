package com.test.jabis.domain.account;

import com.test.jabis.auth.service.AuthService;
import com.test.jabis.user.dao.UserRepository;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.service.UserService;
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
class AuthServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

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

    @Test
    @DisplayName("로그인 처리")
    public void loginTest() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .name("홍길동")
                .password("password")
                .regNo("00000000")
                .userId("hhhhhongse@naver.com")
                .build();
        userService.createAccount(signupRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/szs/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "hhhhhongse@naver.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                // todo json path
                .andDo(print())
                .andExpect(unauthenticated());
    }

}