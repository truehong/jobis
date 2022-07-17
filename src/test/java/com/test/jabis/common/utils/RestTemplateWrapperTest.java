package com.test.jabis.common.utils;

import com.test.jabis.tax.dto.ScrapResponse;
import com.test.jabis.user.dto.SignupRequest;
import com.test.jabis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RestTemplateWrapperTest {

    @Autowired
    RestTemplateWrapper restTemplateWrapper;

    @Autowired
    UserService userService;

    @Value("${apis.scrap-api}")
    private String scrapApi;

    @Test
    void post() {
        SignupRequest signupRequest = SignupRequest.builder()
                    .name("홍길동")
                    .password("password")
                    .regNo("00000000")
                    .userId("hhhhhongse@naver.com")
                    .build();
        userService.createAccount(signupRequest);
        //   String token =  authService.login("hhhhhongse@naver.com", "password");

        Map<String, String> request = new HashMap<>();
        request.put("name", "홍길동");
        request.put("regNo", "860824-1655068");
        HttpHeaders headers = restTemplateWrapper.getDefaultApiHeaders();
        HttpEntity entity = restTemplateWrapper.createJsonEntity(request, headers);
        ResponseEntity<ScrapResponse> result = restTemplateWrapper.callApi(scrapApi, org.springframework.http.HttpMethod.POST, entity,
                new ParameterizedTypeReference<>() {
                });
        ScrapResponse scrapResponse = result.getBody();
        scrapResponse.getData();
    }
}