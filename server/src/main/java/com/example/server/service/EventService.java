package com.example.server.service;

import com.example.server.repository.event.Event;
import com.example.server.repository.event.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public Event getEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("Ивента с id: " + id + " не существует");
        }
        return optionalEvent.get();
    }

    public List<Event> getUserEvents(Long user_id) {
        return eventRepository.findAllByUserId(user_id);
    }

    public void createEvents(List<Event> events) {
        eventRepository.saveAll(events);
    }

    public void deleteEvents(List<Long> ids) {
        eventRepository.deleteAllById(ids);
    }

    public void updateEvents(List<Event> changes) {
        List<Event> events = new ArrayList<>();
        for (Event change : changes) {
            if (change.getId() == null) {
                throw new IllegalArgumentException("Не указан id ивента при обновлении");
            }
            Event event = getEvent(change.getId());
            if (change.getTitle() != null) event.setTitle(change.getTitle());
            if (change.getStart_time() != null) event.setStart_time(change.getStart_time());
            if (change.getEnd_time() != null) event.setEnd_time(change.getEnd_time());
            if (change.getNotification_time() != null) event.setNotification_time(change.getNotification_time());

            events.add(event);
        }
        eventRepository.saveAll(events);
    }
}
