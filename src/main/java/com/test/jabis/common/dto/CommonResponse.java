package com.test.jabis.common.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResponse<T> {
    private HttpStatus code = HttpStatus.OK;

    private String message;
    private T data;

    protected CommonResponse(T data, String message) {this.data = data;
    this.message = message;}

    public static <T> CommonResponse<T> successOf(T data, String message) { return new CommonResponse<>(data, message);}

}
