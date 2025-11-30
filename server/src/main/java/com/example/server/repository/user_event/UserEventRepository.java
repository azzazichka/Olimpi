package com.example.server.repository.user_event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    @Query(value = "SELECT * FROM user_events WHERE user_id = :user_id", nativeQuery = true)
    List<UserEvent> findAllByUserId(Long user_id);

    @Query(value = "SELECT * FROM user_events WHERE user_id = :userId AND contest_id = :contestId", nativeQuery = true)
    UserEvent findByUserIdAndContestId(Long userId, Long contestId);
}
