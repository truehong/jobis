package com.test.jabis.tax.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScrapResponse {
    private String status;

    private Object errors;

    private Object data;
}
