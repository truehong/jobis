package com.test.jabis.tax.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefundTaxCalculator {
    private final Integer inCome;

    public Money createMax() {
        return new Money(0);
    }
    public Money createSale() {
        return new Money(0);
    }
    public Money createRefund() {
        return new Money(0);
    }
}
