package com.example.server.repository.achievement;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "achievements")
@Data
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    List<String> photos_links;
    Long user_id;
    Long contest_id;
}
