package com.example.server.repository.event;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    Date start_time;
    Date end_time;
    Date notification_time;
    Long user_id;
}
