package com.numble.backingserver.account;

import com.numble.backingserver.NumbleAlarmService;
import com.numble.backingserver.account.dto.AccountResponse;
import com.numble.backingserver.account.dto.TransferRequest;
import com.numble.backingserver.exception.CustomException;
import com.numble.backingserver.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AccountController {

    private final AccountService accountService;
    private final NumbleAlarmService numbleAlarmService;

    @PostMapping("/{userId}/account")
    public ResponseEntity<String> openAccount(@PathVariable int userId, @RequestBody Map<String, String> request) {
        List<Account> accountList = accountService.findByUserId(userId);
        if (accountList.size() > 2) {
            throw new CustomException(ErrorCode.EXCEED_ACCOUNT_SIZE);
        }
        Account savedAccount = accountService.save(createAccountEntity(userId, request.get("pin")));
        String accountNumber = savedAccount.getAccountNumber() + savedAccount.getAccountId();
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
        Account account = accountService.findByAccountId(accountId);
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
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
        Account sender = accountService.findByAccountId(accountId);
        Account recipient;

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

        numbleAlarmService.notify(recipient.getAccountId(), "이체 완료");

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/{userId}/account/{accountId}/deposit")
    public ResponseEntity<Integer> deposit(@PathVariable int accountId, @RequestBody Map<String, Integer> request) {
        Account account = accountService.findByAccountId(accountId);
        account.setBalance(account.getBalance() + request.get("money"));
        accountService.save(account);
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }

    @PostMapping("/{userId}/account/{accountId}/withdraw")
    public ResponseEntity<Integer> withdraw(@PathVariable int accountId, @RequestBody Map<String, Integer> request) {
        Account account = accountService.findByAccountId(accountId);
        account.setBalance(account.getBalance() - request.get("money"));
        accountService.save(account);
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/account/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable int accountId) {
        accountService.deleteByAccountId(accountId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}