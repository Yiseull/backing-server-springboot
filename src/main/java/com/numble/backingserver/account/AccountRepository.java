package com.numble.backingserver.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account save(Account account);

    @Override
    Optional<Account> findById(Integer id);
}
