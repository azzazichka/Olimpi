package com.example.server.repository.achievement;

import com.example.server.repository.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
