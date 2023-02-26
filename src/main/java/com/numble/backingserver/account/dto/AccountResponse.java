package com.numble.backingserver.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AccountResponse {

    private String accountNumber;
    private int balance;
}
