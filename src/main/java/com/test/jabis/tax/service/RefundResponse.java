package com.test.jabis.tax.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {
    @JsonProperty("이름")
    String name;
    @JsonProperty("한도")
    String max;
    @JsonProperty("공제액")
    String sale;
    @JsonProperty("환급액")
    String refund;
}
