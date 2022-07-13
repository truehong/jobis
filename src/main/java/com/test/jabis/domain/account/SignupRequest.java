package com.test.jabis.domain.account;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotNull
    String userId;
    @NotNull
    String password;
    String name;
    String regNo;
}
