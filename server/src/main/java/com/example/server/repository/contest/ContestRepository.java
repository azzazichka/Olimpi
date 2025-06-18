package com.example.server.repository.contest;

import com.example.server.repository.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
