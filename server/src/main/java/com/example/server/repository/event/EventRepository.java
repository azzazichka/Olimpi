package com.example.server.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM events WHERE user_id = :user_id", nativeQuery = true)
    List<Event> findAllByUserId(Long user_id);
}
