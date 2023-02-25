package com.numble.backingserver.user.dto;

import com.numble.backingserver.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class UserLoginResponse {

    private int userId;
    private String email;
    private String name;
    private String phoneNumber;

    public void toDto(User user) {
        userId = user.getUserId();
        email = user.getEmail();
        name = user.getName();
        phoneNumber = user.getPhoneNumber();
    }
}
