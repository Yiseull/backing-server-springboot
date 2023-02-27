package com.numble.backingserver.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;

    @Transactional
    Friend save(Friend friend) {
        return friendRepository.save(friend);
    }
}
