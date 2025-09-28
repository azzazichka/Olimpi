package com.example.server.controller;

import com.example.server.repository.user_event.UserEvent;
import com.example.server.repository.user_key.UserKey;
import com.example.server.service.UserEventService;
import com.example.server.service.UserKeyService;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user_events")
public class UserEventController {
    private final UserEventService userEventService;
    private final UserKeyService userKeyService;

    public UserEventController(UserEventService userEventService, UserKeyService userKeyService) {
        this.userEventService = userEventService;
        this.userKeyService = userKeyService;
    }


    @GetMapping
    public List<UserEvent> getUserEvents(@RequestHeader("x-api-key") String key) {
        Long user_id = userKeyService.getUserIdByKey(key);

        return userEventService.getUserEvents(user_id);
    }

    @PostMapping
    public void createEvent(@RequestBody UserEvent event, @RequestHeader("x-api-key") String key) throws AuthException {
        userKeyService.checkAuthUserEvent(event, key);
        userEventService.createEvent(event);
    }

    @DeleteMapping
    public void deleteEvent(@RequestParam Long id, @RequestHeader("x-api-key") String key) throws AuthException {
        userKeyService.checkAuthUserEvent(userEventService.getEvent(id), key);
        userEventService.deleteEventById(id);
    }

    @PutMapping
    public void updateEvent(@RequestBody UserEvent changes, @RequestHeader("x-api-key") String key) throws AuthException {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id userEvent при обновлении");
        }

        userKeyService.checkAuthUserEvent(userEventService.getEvent(changes.getId()), key);
        userEventService.updateEvent(changes);
    }
}
