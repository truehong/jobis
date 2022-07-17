package com.test.jabis.tax.controller;

import com.test.jabis.auth.annotations.AuthCheck;
import com.test.jabis.auth.dto.TokenRequest;
import com.test.jabis.tax.dto.ScrapResponse;
import com.test.jabis.tax.service.TaxService;
import com.test.jabis.user.annotation.UserInfo;
import com.test.jabis.user.dao.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaxController {
    private final TaxService taxService;

    @AuthCheck
    @PostMapping("/szs/scrap")
    public ScrapResponse getUserScrap(@UserInfo User user, @TokenRequest String token) {
        ScrapResponse scrap = taxService.getUserScrap(user, token);
        return scrap;
    }

    @AuthCheck
    @GetMapping("/szs/refund")
    public Object getRefund(@UserInfo User user, @TokenRequest String token) {
        Object refund = taxService.getRefund(user, token);
        return refund;
    }

}
