package com.numble.backingserver.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;

    @Transactional
    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }

    @Transactional(readOnly = true)
    public List<Friend> findByUser1OrUser2(int user1, int user2) {
        return friendRepository.findByUser1OrUser2(user1, user2);
    }

    @Transactional
    public void deleteByUser1AndUser2(int user1, int user2) {
        friendRepository.deleteByUser1AndUser2(user1, user2);
    }
}
