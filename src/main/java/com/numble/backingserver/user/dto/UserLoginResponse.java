package com.numble.backingserver.user.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
public class UserLoginResponse {

    private int userId;
    private String email;
    private String name;
    private String phoneNumber;

}
