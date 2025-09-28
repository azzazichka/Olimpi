package com.example.server.service;

import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.attachment.Attachment;
import com.example.server.repository.user.User;
import com.example.server.repository.user_event.UserEvent;
import com.example.server.repository.user_key.UserKey;
import com.example.server.repository.user_key.UserKeyRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserKeyService {
    private final UserKeyRepository userKeyRepository;
    private final AchievementService achievementService;
    private final UserService userService;

    public UserKeyService(UserKeyRepository userKeyRepository, AchievementService achievementService, UserService userService) {
        this.userKeyRepository = userKeyRepository;
        this.achievementService = achievementService;
        this.userService = userService;
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

    public void checkAuthAttachment(Attachment attachment, String key) throws AuthException {
        Achievement achievement = achievementService.getAchievement(attachment.getAchievement_id());
        checkAuthAchievement(achievement, key);
    }

    public void checkAuthAchievement(Achievement achievement, String key) throws AuthException {
        Long user_id = getUserIdByKey(key);

        if (!Objects.equals(achievement.getUser_id(), user_id)) {
            throw new AuthException("Ошибка аутентификации");
        }
    }

    public void checkAuthUserEvent(UserEvent userEvent, String key) throws AuthException {
        Long user_id = getUserIdByKey(key);

        if (!Objects.equals(userEvent.getUser_id(), user_id)) {
            throw new AuthException("Ошибка аутентификации");
        }
    }

    public void checkAccessRights(String key, int access_lvl) throws AuthException {
        Long user_id = getUserIdByKey(key);
        User user = userService.getUser(user_id);

        if (user.getAccess_lvl() < access_lvl) {
            throw new AuthException("Недостаточно прав");
        }
    }
}
