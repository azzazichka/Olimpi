package com.example.server.controller;

import com.example.server.repository.user.User;
import com.example.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/all_users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
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

    @PutMapping(path = "{id}")
    public void updateUser(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Integer access_lvl) {
        userService.updateUser(id, name, email, password, access_lvl);
    }
}
