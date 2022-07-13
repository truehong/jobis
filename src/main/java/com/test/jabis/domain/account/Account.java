package com.test.jabis.domain.account;

import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// todo : @EqualsAndHashCode
@Table(name = "account")
public class Account {
    @Id @GeneratedValue
    @Column(name = "user_no", nullable = false)
    Long userNo;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(unique = true, name = "password", nullable = false)
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
