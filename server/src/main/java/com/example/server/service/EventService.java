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

    public Event getEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("Ивента с id: " + id + " не существует");
        }
        return optionalEvent.get();
    }

    public List<Event> getUserEvents(Long user_id) {
        return eventRepository.getEventsByUserId(user_id);
    }

    public void createEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        getEvent(id);
        eventRepository.deleteById(id);
    }

    public void updateEvent(Event changes) {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id ивента при обновлении");
        }
        Event event = getEvent(changes.getId());
        if (changes.getTitle() != null) event.setTitle(changes.getTitle());
        if (changes.getStart_time() != null) event.setStart_time(changes.getStart_time());
        if (changes.getEnd_time() != null) event.setEnd_time(changes.getEnd_time());
        if (changes.getNotification_time() != null) event.setNotification_time(changes.getNotification_time());

        eventRepository.save(event);
    }
}
// TODO: в апдейты передавать листы