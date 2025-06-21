package com.example.server.controller;

import com.example.server.repository.user.User;
import com.example.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
//    @GetMapping(path = "/all_users")
//    public List<User> getUsers() {
//        return userService.getAllUsers();
//    }
    @GetMapping
    public User getUser(@RequestParam String email, @RequestParam String password) {
        return userService.getUser(email, password);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping
    public void updateUser(@RequestBody User changes) {
        userService.updateUser(changes);
    }
}
