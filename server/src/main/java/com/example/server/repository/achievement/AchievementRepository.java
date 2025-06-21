package com.example.server.repository.achievement;

import com.example.server.repository.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    @Query(value = "SELECT * FROM achievements WHERE user_id = :user_id", nativeQuery = true)
    List<Achievement> findAllByUserId(Long user_id);
}
