package com.example.server.service;

import com.example.server.repository.user.User;
import com.example.server.repository.user.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Юзера с email: " + email + " не существует");
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalStateException("У юзера с email: " + email + " пароль отличается от: " + password);
        }
        return user;
    }
    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Юзера с id: " + id + " не существует");
        }
        return optionalUser.get();
    }
    public void createUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("Юзер с email: " + user.getEmail() + " уже существует");
        }

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        getUser(id);
        userRepository.deleteById(id);
    }

    public void updateUser(@NonNull Long id, String name, String email, String password, Integer accessLvl) {
        User user = getUser(id);
        if (name != null) user.setName(name);
        if (email != null && !user.getEmail().equals(email)) {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                throw new IllegalStateException("Юзер с email: " + user.getEmail() + " уже существует");
            }
            user.setEmail(email);
        }
        if (password != null) user.setPassword(password);
        if (accessLvl != null) user.setAccess_lvl(accessLvl);

        userRepository.save(user);
    }
}
