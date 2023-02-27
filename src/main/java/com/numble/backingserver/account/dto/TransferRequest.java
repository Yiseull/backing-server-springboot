package com.numble.backingserver.account.dto;

import lombok.Getter;

@Getter
public class TransferRequest {

    private String accountNumber;
    private int money;

}
