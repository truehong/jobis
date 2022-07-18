package com.test.jabis.user.dao;

import com.test.jabis.user.dto.SignupRequest;
import io.jsonwebtoken.Claims;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// todo : @EqualsAndHashCode
@Table(name = "user_account")
public class User {
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    String userId;

    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "name")
    String name;
    @Column(name = "reg_no")
    String regNo;

    public static User create(SignupRequest signupRequest) {
        return User.builder()
                .userId(signupRequest.getUserId())
                .password(signupRequest.getPassword())
                .name(signupRequest.getName())
                .regNo(signupRequest.getRegNo())
                .build();
    }

    public static User create(Claims claims) {
        return User.builder()
                .name(String.valueOf(claims.get("user_name")))
                .name(String.valueOf(claims.get("user_id")))
                .name(String.valueOf(claims.get("reg_no")))
                .name(String.valueOf(claims.get("user_name")))
                .build();
    }
}
