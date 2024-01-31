package org.halimatussyadiyah.pmb.service;


import org.halimatussyadiyah.pmb.domain.UserEvent;
import org.halimatussyadiyah.pmb.enumeration.EventType;

import java.util.Collection;

public interface EventService {
    Collection<UserEvent> getEventsByUserId(Long userId);

    void addUserEvent(String email, EventType eventType, String device, String ipAddress);

    void addUserEvent(Long userId, EventType eventType, String device, String ipAddress);

}
