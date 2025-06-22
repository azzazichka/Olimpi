package com.example.server.controller;

import com.example.server.repository.event.Event;
import com.example.server.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping
    public List<Event> getUserEvents(@RequestParam Long user_id) {
        return eventService.getUserEvents(user_id);
    }

    @PostMapping
    public void createEvents(@RequestBody List<Event> events) {
        eventService.createEvents(events);
    }

    @DeleteMapping
    public void deleteEvents(@RequestBody List<Long> ids) {
        eventService.deleteEvents(ids);
    }

    @PutMapping
    public void updateEvents(@RequestBody List<Event> changes) {
        eventService.updateEvents(changes);
    }
}
