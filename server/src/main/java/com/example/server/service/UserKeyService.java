package com.example.server.service;

import com.example.server.repository.user_key.UserKeyRepository;
import org.springframework.stereotype.Service;

@Service
public class UserKeyService {
    private final UserKeyRepository userKeyRepository;

    public UserKeyService(UserKeyRepository userKeyRepository) {
        this.userKeyRepository = userKeyRepository;
    }


}
