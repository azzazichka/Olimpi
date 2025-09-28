package com.example.server.controller;

import com.example.server.repository.user.User;
import com.example.server.service.UserKeyService;
import com.example.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/users_keys")
public class UserKeyController {
    private final UserKeyService userKeyService;
    private final UserService userService;

    public UserKeyController(UserKeyService userKeyService, UserService userService) {
        this.userKeyService = userKeyService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> logInUser(
            @RequestHeader String email,
            @RequestHeader String password
    ) throws NoSuchAlgorithmException {
        Map<String, String> data = new HashMap<>();

        User user;
        try {
            user = userService.getUser(email, password);
        } catch (IllegalStateException e) {
            data.put("message", e.getMessage());
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        String key = userKeyService.generateUserKey(user);

        data.put("message", "Log in success");
        data.put("x-api-key", key);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping
    public void logOutUser(@RequestHeader("x-api-key") String key) {
        userKeyService.deleteUserKey(key);
    }
}
