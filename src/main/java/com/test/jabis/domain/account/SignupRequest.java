package com.test.jabis.domain.account;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotNull
    String userId;
    @NotNull
    String password;
    String name;
    String regNo;
}
