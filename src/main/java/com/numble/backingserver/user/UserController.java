package com.numble.backingserver.user;

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

    @PostMapping
    public ResponseEntity<String> join(@RequestBody UserJoinRequest request) {
        userService.save(request.toEntity());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody Map<String, String> request) {
        Optional<User> selectedUser = userService.findByEmailAndPassword(request.get("email"), request.get("password"));
        if (selectedUser.isPresent()) {
            UserResponse user = createUserLoginResponse(selectedUser.get());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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