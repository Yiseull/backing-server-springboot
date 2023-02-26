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
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Account> findByUserId(Integer userId) {
        return accountRepository.findByUserId(userId);
    }
}
