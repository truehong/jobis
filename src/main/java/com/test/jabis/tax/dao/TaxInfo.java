package com.test.jabis.tax.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seq;
    @Column(name = "user_id", unique = true)
    String userId;
    @Column(name = "year_sum")
    Integer income;
    @Column(name = "output_tax")
    Integer outputTax;

    public static TaxInfo create(Integer income,Integer tax,String userId) {
        return TaxInfo.builder()
                .income(income)
                .outputTax(tax)
                .userId(userId)
                .build();
    }
}
