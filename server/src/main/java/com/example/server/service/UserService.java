package com.example.server.service;

import com.example.server.repository.user.User;
import com.example.server.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public User getUser(String email, String password) throws NoSuchAlgorithmException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Юзера с email: " + email + " не существует");
        }
        User user = optionalUser.get();
        password = getHashUserPassword(new User("", email, password));
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

    public String getHashUserPassword(User user) throws NoSuchAlgorithmException {
        String hash_user_password = user.getPassword() + user.getEmail();

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash_bytes = digest.digest(hash_user_password.getBytes(StandardCharsets.UTF_8));
        hash_user_password = new String(hash_bytes, StandardCharsets.UTF_8);

        return hash_user_password;
    }

    public void createUser(User user) throws NoSuchAlgorithmException {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("Юзер с email: " + user.getEmail() + " уже существует");
        }

        user.setAccess_lvl(0);
        user.setPassword(getHashUserPassword(user));

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        getUser(id);
        userRepository.deleteById(id);
    }

    public void updateUser(User changes) throws NoSuchAlgorithmException {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id пользователя при обновлении");
        }
        Long user_id = changes.getId();
        User user = getUser(user_id);

        if (changes.getName() != null) user.setName(changes.getName());
        if (changes.getEmail() != null && !user.getEmail().equals(changes.getEmail())) {
            Optional<User> optionalUser = userRepository.findByEmail(changes.getEmail());
            if (optionalUser.isPresent()) {
                throw new IllegalStateException("Юзер с email: " + user.getEmail() + " уже существует");
            }
            user.setEmail(changes.getEmail());
        }
        if (changes.getPassword() != null) user.setPassword(getHashUserPassword(changes));

        userRepository.save(user);
    }
}
