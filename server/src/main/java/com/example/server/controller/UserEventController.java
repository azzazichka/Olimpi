package com.example.server.controller;

import com.example.server.repository.user_event.UserEvent;
import com.example.server.service.UserEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user_events")
public class UserEventController {
    private final UserEventService userEventService;

    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }


    @GetMapping
    public List<UserEvent> getUserEvents(@RequestParam Long user_id) {
        return userEventService.getUserEvents(user_id);
    }

    @PostMapping
    public void createEvent(@RequestBody UserEvent event) {
        userEventService.createEvent(event);
    }

    @DeleteMapping
    public void deleteEvent(@RequestParam Long id) {
        userEventService.deleteEventById(id);
    }

    @PutMapping
    public void updateEvent(@RequestBody UserEvent changes) {
        userEventService.updateEvent(changes);
    }
}
