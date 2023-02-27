package com.numble.backingserver.friend;

import com.numble.backingserver.user.User;
import com.numble.backingserver.user.UserService;
import com.numble.backingserver.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("/{userId}/friend/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable int userId, @PathVariable int friendId) {
        Friend friend = new Friend();
        if (userId < friendId) {
            friend.setUser1(userId);
            friend.setUser2(friendId);
        } else {
            friend.setUser1(friendId);
            friend.setUser2(userId);
        }
        friendService.save(friend);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{userId}/friend")
    public ResponseEntity<List<UserResponse>> getFriendList(@PathVariable int userId) {
        List<Friend> findFriendList = friendService.findByUser1OrUser2(userId, userId);
        List<UserResponse> friendList = extractFriendList(userId, findFriendList);
        return new ResponseEntity<>(friendList, HttpStatus.OK);
    }

    private List<UserResponse> extractFriendList(int userId, List<Friend> findFriendList) {
        List<UserResponse> list = new ArrayList<>();
        for (Friend friend : findFriendList) {
            int friendId = friend.getUser1();
            if (friendId == userId) {
                friendId = friend.getUser2();
            }
            list.add(createUserResponse(userService.findByUserId(friendId)));
        }
        return list;
    }

    private UserResponse createUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
