package com.numble.backingserver.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account save(Account account);

    @Override
    Optional<Account> findById(Integer id);

    List<Account> findByUserId(Integer userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    void deleteByAccountId(int accountId);
}
