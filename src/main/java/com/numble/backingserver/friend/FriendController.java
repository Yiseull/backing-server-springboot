package com.numble.backingserver.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/{userId}/friend/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable int userId, @PathVariable int friendId) {
        friendService.save(new Friend(new FriendId(userId, friendId)));
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
