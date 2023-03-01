package com.numble.backingserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Transactional
    public void deleteByUserId(int userId) {
        userRepository.deleteByUserId(userId);
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
