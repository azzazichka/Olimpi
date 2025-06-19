package com.example.server.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM events WHERE user_id = :user_id", nativeQuery = true)
    List<Event> getEventsByUserId(Long user_id);
}
