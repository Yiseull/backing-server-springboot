package com.numble.backingserver.user.dto;

import com.numble.backingserver.user.User;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}
