package com.numble.backingserver.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findByAccountId(int accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    @Transactional(readOnly = true)
    public List<Account> findByUserId(Integer userId) {
        return accountRepository.findByUserId(userId);
    }

    @Transactional
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Transactional
    public void deleteByAccountId(int accountId) {
        accountRepository.deleteByAccountId(accountId);
    }
}
