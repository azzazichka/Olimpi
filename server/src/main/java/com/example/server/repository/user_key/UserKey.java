package com.example.server.repository.user_key;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users_keys")
@Data
public class UserKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private String key;
}
