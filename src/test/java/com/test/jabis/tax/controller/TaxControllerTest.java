package com.test.jabis.tax.controller;

import com.test.jabis.auth.jwt.JwtTokenProvider;
import com.test.jabis.user.dao.User;
import com.test.jabis.user.dao.UserRepository;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaxControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Test
    @DisplayName("스크랩 정보 조회")
    public void userScrapControllerTest() throws Exception {


        User user = User.builder()
                        .userId("hhhhhongse@naver.com")
                        .password("password")
                        .regNo("860824-1655068")
                        .name("홍길동")
                        .build();
        userRepository.save(user);
        String token = tokenProvider.generateToken(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/szs/scrap")
                        .header("Authorization", "Bearer "+ token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                // todo json path
                .andDo(print())
                .andExpect(unauthenticated());
    }
}