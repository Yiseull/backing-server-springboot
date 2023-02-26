package com.numble.backingserver.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{userId}/account")
    public ResponseEntity<String> createAccount(@PathVariable int userId, @RequestBody Map<String, String> request) {
        Account savedAccount = accountService.save(createAccountEntity(userId, request.get("pin")));
        String accountNumber = savedAccount.getAccountNumber() + Integer.toString(savedAccount.getAccountId());
        log.info("accountNumber={}", accountNumber);
        savedAccount.setAccountNumber(accountNumber);
        accountService.save(savedAccount);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    private Account createAccountEntity(int userId, String pin) {
        Account account = new Account();
        account.setPin(pin);
        account.setUserId(userId);
        return account;
    }
}