package com.example.server.service;

import com.example.server.repository.event.Event;
import com.example.server.repository.event.EventRepository;
import com.example.server.repository.user.User;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getUserEvents(Long user_id) {
        return eventRepository.getEventsByUserId(user_id);
    }

    public void createEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("Ивента с id: " + id + " не существует");
        }
        eventRepository.deleteById(id);
    }

    public void updateEvent(@NonNull Long id, String title, Date start_time, Date end_time, Date notification_time) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("Ивента с id: " + id + " не существует");
        }

        Event event = optionalEvent.get();
        if (title != null) event.setTitle(title);
        if (start_time != null) event.setStart_time(start_time);
        if (end_time != null) event.setEnd_time(end_time);
        if (notification_time != null) event.setNotification_time(notification_time);

        eventRepository.save(event);
    }
}
