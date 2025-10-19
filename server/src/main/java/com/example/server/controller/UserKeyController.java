package com.example.server.controller;

import com.example.server.repository.user.User;
import com.example.server.service.UserKeyService;
import com.example.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "api/users_keys")
public class UserKeyController {
    private final UserKeyService userKeyService;
    private final UserService userService;

    public UserKeyController(UserKeyService userKeyService, UserService userService) {
        this.userKeyService = userKeyService;
        this.userService = userService;
    }

    @GetMapping
    public String logInUser(
            @RequestHeader String email,
            @RequestHeader String password
    ) throws NoSuchAlgorithmException {
        User user = userService.getUser(email, password);

        return userKeyService.generateUserKey(user);
    }

    @DeleteMapping
    public void logOutUser(@RequestHeader("x-api-key") String key) {
        userKeyService.deleteUserKey(key);
    }
}
