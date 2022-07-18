package com.test.jabis.tax.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Money {
    private final Integer money;

    @Override
    public String toString() {
        Integer tmpMoney = money / 1000;
        return tmpMoney + " 천원";
    }
}
