package com.numble.backingserver.account;

import com.numble.backingserver.account.dto.AccountResponse;
import com.numble.backingserver.user.User;
import com.numble.backingserver.user.dto.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{userId}/account/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable int accountId) {
        Optional<Account> findAccount = accountService.findById(accountId);
        if (findAccount.isPresent()) {
            AccountResponse account = createAccountResponse(findAccount.get());
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private AccountResponse createAccountResponse(Account findAccount) {
        return AccountResponse.builder()
                .accountNumber(findAccount.getAccountNumber())
                .balance(findAccount.getBalance())
                .build();
    }
}