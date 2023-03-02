package com.numble.backingserver.user;

import com.numble.backingserver.exception.CustomException;
import com.numble.backingserver.exception.ErrorCode;
import com.numble.backingserver.friend.FriendService;
import com.numble.backingserver.user.dto.UserJoinRequest;
import com.numble.backingserver.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<String> join(@RequestBody UserJoinRequest request) {
        userService.save(request.toEntity());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody Map<String, String> request) {
        Optional<User> selectedUser = userService.findByEmailAndPassword(request.get("email"), request.get("password"));
        UserResponse user = createUserLoginResponse(selectedUser.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private UserResponse createUserLoginResponse(User findUser) {
        return UserResponse.builder()
                .userId(findUser.getUserId())
                .email(findUser.getEmail())
                .name(findUser.getName())
                .phoneNumber(findUser.getPhoneNumber())
                .build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userService.deleteByUserId(userId);
        friendService.deleteByUser1OrUser2(userId, userId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/email-check")
    public ResponseEntity<Integer> checkEmail(@RequestBody Map<String, String> request) {
        Optional<User> selectedUser = userService.findByEmail(request.get("email"));
        if (selectedUser.isPresent()) {
            User user = selectedUser.get();
            return new ResponseEntity<>(user.getUserId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(-1, HttpStatus.OK);
    }
}