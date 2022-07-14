package com.test.jabis.tax.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;

@Getter
@RequiredArgsConstructor
public class Money {
    private final Integer money;

    @Override
    public String toString() {
        return "Money{" +
                "money='" + money + '\'' +
                '}';
    }
}
