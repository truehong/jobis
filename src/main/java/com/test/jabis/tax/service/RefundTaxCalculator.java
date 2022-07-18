package com.test.jabis.tax.service;

import com.test.jabis.tax.dto.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefundTaxCalculator {
    private final Integer inCome;
    private final Integer tax;

    public Money createMax() {
        double cal;
        if (inCome < 33000000) {
            return new Money(740000);
        } else if (inCome > 33000000 || inCome <= 77000000) {
            cal = 740000 - ((inCome - 33000000) * 0.008);
            if (cal < 660000) {
                return new Money(660000);
            }
            return new Money((int) cal);
        } else {
            cal = 660000 - ((inCome - 70000000) / 2);
            if (cal < 500000) {
                return new Money(500000);
            }
            return new Money((int) cal);
        }
    }
    public Money createSale() {
        return new Money(0);
    }
    public Money createRefund() {
        return new Money(0);
    }
}
