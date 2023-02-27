package com.numble.backingserver.friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private int friendId;
    private int user1;
    private int user2;

    public Friend(int user1, int user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
