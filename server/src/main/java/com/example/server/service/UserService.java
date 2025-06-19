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

    public void updateUser(User changes) {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id юзера при обновлении");
        }
        User user = getUser(changes.getId());
        if (changes.getName() != null) user.setName(changes.getName());
        if (changes.getEmail() != null && !user.getEmail().equals(changes.getEmail())) {
            Optional<User> optionalUser = userRepository.findByEmail(changes.getEmail());
            if (optionalUser.isPresent()) {
                throw new IllegalStateException("Юзер с email: " + user.getEmail() + " уже существует");
            }
            user.setEmail(changes.getEmail());
        }
        if (changes.getPassword() != null) user.setPassword(changes.getPassword());
        if (changes.getAccess_lvl() != null) user.setAccess_lvl(changes.getAccess_lvl());

        userRepository.save(user);
    }
}
