package com.test.jabis.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScrapResponse<T> {
    private String status;

    private Object errors;

    private T data;
}
