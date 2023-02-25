package com.numble.backingserver.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    void deleteByUserId(int userId);

}
