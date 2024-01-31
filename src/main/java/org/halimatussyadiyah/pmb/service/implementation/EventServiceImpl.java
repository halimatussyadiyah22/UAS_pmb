package org.halimatussyadiyah.pmb.service.implementation;

import lombok.RequiredArgsConstructor;
import org.halimatussyadiyah.pmb.domain.UserEvent;
import org.halimatussyadiyah.pmb.enumeration.EventType;
import org.halimatussyadiyah.pmb.repository.EventRepository;
import org.halimatussyadiyah.pmb.service.EventService;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    @Override
    public Collection<UserEvent> getEventsByUserId(Long userId) {
        return eventRepository.getEventsByUserId(userId);
    }

    @Override
    public void addUserEvent(String email, EventType eventType, String device, String ipAddress) {
        eventRepository.addUserEvent(email, eventType, device, ipAddress);

    }

    @Override
    public void addUserEvent(Long userId, EventType eventType, String device, String ipAddress) {

    }
}
