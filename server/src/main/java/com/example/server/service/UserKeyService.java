package com.example.server.service;

import com.example.server.repository.user.User;
import com.example.server.repository.user_key.UserKey;
import com.example.server.repository.user_key.UserKeyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserKeyService {
    private final UserKeyRepository userKeyRepository;

    public UserKeyService(UserKeyRepository userKeyRepository) {
        this.userKeyRepository = userKeyRepository;
    }


    public String generateUserKey(User user) {
        UUID user_api_key_uuid = UUID.randomUUID();
        String user_api_key = user_api_key_uuid.toString().replaceAll("-", "");

        UserKey userKey = new UserKey();
        userKey.setUser_id(user.getId());
        userKey.setKey(user_api_key);

        userKeyRepository.save(userKey);

        return user_api_key;
    }

    public void deleteUserKey(String key) {
        userKeyRepository.deleteByKey(key);
    }

    public Long getUserIdByKey(String key) {
        Optional<UserKey> userKeyOptional = userKeyRepository.findByKey(key);

        if (userKeyOptional.isEmpty()) {
            throw new IllegalStateException("Пользователя с x-api-key: " + key + " не существует!");
        }

        return userKeyOptional.get().getUser_id();
    }
}
