package com.example.server.service;

import com.example.server.repository.user_event.UserEvent;
import com.example.server.repository.user_event.UserEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEventService {
    private final UserEventRepository eventRepository;

    public UserEventService(UserEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public UserEvent getEvent(Long id) {
        Optional<UserEvent> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("Ивента с id " + id + " не существует");
        }
        return optionalEvent.get();
    }

    public List<UserEvent> getUserEvents(Long user_id) {
        return eventRepository.findAllByUserId(user_id);
    }

    public void createEvent(UserEvent event) {
        eventRepository.save(event);
    }

    public void deleteEventById(Long id) {
        getEvent(id);
        eventRepository.deleteById(id);
    }

    public void updateEvent(UserEvent changes) {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id ивента при обновлении");
        }
        UserEvent event = getEvent(changes.getId());
        if (changes.getTitle() != null) event.setTitle(changes.getTitle());
        if (changes.getStart_time() != null) event.setStart_time(changes.getStart_time());
        if (changes.getEnd_time() != null) event.setEnd_time(changes.getEnd_time());
        if (changes.getNotification_time() != null) event.setNotification_time(changes.getNotification_time());

        eventRepository.save(event);
    }
}
