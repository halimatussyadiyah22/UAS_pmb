package org.halimatussyadiyah.pmb.listener;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.halimatussyadiyah.pmb.event.NewUserEvent;
import org.halimatussyadiyah.pmb.service.EventService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.halimatussyadiyah.pmb.utils.RequestUtils.getDevice;
import static org.halimatussyadiyah.pmb.utils.RequestUtils.getIpAddress;


@Component
@RequiredArgsConstructor
public class NewUserEventListener {
    private final EventService eventService;
    private final HttpServletRequest request;

    @EventListener
    public void onNewUserEvent(NewUserEvent event) {
        eventService.addUserEvent(event.getEmail(), event.getType(), getDevice(request), getIpAddress(request));
    }
}

