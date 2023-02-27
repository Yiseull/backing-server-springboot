package com.numble.backingserver.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FriendId implements Serializable {

    @Column(name = "friend_id_1")
    private int friendId1;
    @Column(name = "friend_id_2")
    private int friendId2;

}
