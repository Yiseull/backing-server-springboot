package com.numble.backingserver.user;

import com.numble.backingserver.user.dto.UserJoinRequest;
import com.numble.backingserver.user.dto.UserLoginResponse;
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
    public ResponseEntity<UserLoginResponse> login(@RequestBody Map<String, String> request) {
        Optional<User> selectedUser = userService.findByEmailAndPassword(request.get("email"), request.get("password"));
        if (selectedUser.isPresent()) {
            UserLoginResponse user = createUserLoginResponse(selectedUser.get());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private UserLoginResponse createUserLoginResponse(User findUser) {
        return UserLoginResponse.builder()
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
    public ResponseEntity<String> checkEmail(@RequestBody Map<String, String> request) {
        Optional<User> selectedUser = userService.findByEmail(request.get("email"));
        if (selectedUser.isPresent()) {
            return new ResponseEntity<>("Exist", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not exist", HttpStatus.OK);
    }
}