package com.numble.backingserver.account;

import com.numble.backingserver.account.dto.AccountResponse;
import com.numble.backingserver.account.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{userId}/account")
    public ResponseEntity<String> openAccount(@PathVariable int userId, @RequestBody Map<String, String> request) {
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
    public ResponseEntity<Integer> getAccount(@PathVariable int accountId) {
        Optional<Account> findAccount = accountService.findById(accountId);
        if (findAccount.isPresent()) {
            Account account = findAccount.get();
            return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/account")
    public ResponseEntity<List<AccountResponse>> getAccountList(@PathVariable int userId) {
        List<AccountResponse> accountList = new ArrayList<>();
        for (Account account : accountService.findByUserId(userId)) {
            accountList.add(createAccountResponse(account));
        }
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    private AccountResponse createAccountResponse(Account findAccount) {
        return AccountResponse.builder()
                .accountId(findAccount.getAccountId())
                .accountNumber(findAccount.getAccountNumber())
                .build();
    }

    @PostMapping("/{userId}/account/{accountId}")
    public ResponseEntity<String> transfer(@PathVariable int accountId, @RequestBody TransferRequest request) {
        Optional<Account> myAccount = accountService.findById(accountId);
        Account sender = myAccount.get();
        Account recipient = null;

        Optional<Account> findAccount = accountService.findByAccountNumber(request.getAccountNumber());
        if (findAccount.isPresent()) {
            recipient = findAccount.get(); }
        else {
            return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
        }

        int money = request.getMoney();
        sender.setBalance(sender.getBalance() - money);
        recipient.setBalance(recipient.getBalance() + money);
        accountService.save(sender);
        accountService.save(recipient);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}