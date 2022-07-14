package com.test.jabis.domain.account;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    @NotNull
    String userId;
    @NotNull
    String password;
    String name;
    String regNo;
}
