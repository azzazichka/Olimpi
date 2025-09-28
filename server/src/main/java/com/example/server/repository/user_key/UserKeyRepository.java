package com.example.server.repository.user_key;

import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserKeyRepository extends JpaRepository<UserKey, Long> {
    @Query(value = "SELECT * FROM users_keys WHERE key = :key", nativeQuery = true)
    Optional<UserKey> findByKey(String key);

    @Modifying
    @Query(value = "DELETE FROM users_keys WHERE key = :key", nativeQuery = true)
    void deleteByKey(String key);
}
