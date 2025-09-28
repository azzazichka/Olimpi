package com.example.server.controller;

import com.example.server.repository.user.User;
import com.example.server.service.UserKeyService;
import com.example.server.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;
    private final UserKeyService userKeyService;

    public UserController(UserService userService, UserKeyService userKeyService) {
        this.userService = userService;
        this.userKeyService = userKeyService;
    }

    @GetMapping
    public User getUser(@RequestHeader("x-api-key") String key) {
        Long user_id = userKeyService.getUserIdByKey(key);
        return userService.getUser(user_id);
    }

    @PostMapping
    public void createUser(@RequestBody User user) throws NoSuchAlgorithmException {

        userService.createUser(user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@RequestHeader("x-api-key") String key) {
        Long user_id = userKeyService.getUserIdByKey(key);
        userService.deleteUser(user_id);
    }

    @PutMapping
    public void updateUser(@RequestBody User changes, @RequestHeader("x-api-key") String key) throws NoSuchAlgorithmException {
        changes.setId(userKeyService.getUserIdByKey(key));
        userService.updateUser(changes);
    }
}
