package com.numble.backingserver.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AccountResponse {

    private int accountId;
    private String accountNumber;
}
