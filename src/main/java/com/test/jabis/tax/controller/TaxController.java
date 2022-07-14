package com.test.jabis.tax.controller;

import com.test.jabis.common.annotations.AuthCheck;
import com.test.jabis.common.dto.CommonResponse;
import com.test.jabis.common.dto.ScrapResponse;
import com.test.jabis.tax.service.TaxService;
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
    public ScrapResponse getUserScrap() {
        ScrapResponse scrap = taxService.getUserScrap();
        return scrap;
    }

    @AuthCheck
    @GetMapping("/szs/refund")
    public Object getRefund() {
        Object refund = taxService.getRefund();
        return refund;
    }

}
