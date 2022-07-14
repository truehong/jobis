package com.test.jabis.user.dao;

import com.test.jabis.user.dto.SignupRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// todo : @EqualsAndHashCode
@Table(name = "user_account")
public class User {
    @Id @GeneratedValue
    @Column(name = "user_no", nullable = false)
    Long userNo;

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
}
