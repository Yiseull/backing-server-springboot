package com.numble.backingserver.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    Friend save(Friend friend);
}
