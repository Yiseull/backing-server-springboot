package com.numble.backingserver.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    Friend save(Friend friend);

    List<Friend> findByUser1OrUser2(int user1, int user2);

    void deleteByUser1AndUser2(int user1, int user2);

    void deleteByUser1OrUser2(int user1, int user2);
}
