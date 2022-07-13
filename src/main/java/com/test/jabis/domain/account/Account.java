package com.test.jabis.domain.account;

import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// todo : @EqualsAndHashCode
public class Account {
    @Id
    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "password", nullable = false)
    String password;
    String name;
    @Column(name = "reg_no")
    String regNo;

    public static Account create(SignupRequest signupRequest) {
        return Account.builder()
                .userId(signupRequest.getUserId())
                .password(signupRequest.getPassword())
                .name(signupRequest.getName())
                .regNo(signupRequest.getRegNo())
                .build();
    }
}
