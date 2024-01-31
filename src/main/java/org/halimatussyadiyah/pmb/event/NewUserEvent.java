package org.halimatussyadiyah.pmb.event;

import lombok.Getter;
import lombok.Setter;
import org.halimatussyadiyah.pmb.enumeration.EventType;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class NewUserEvent extends ApplicationEvent {
    private EventType type;
    private String email;

    public NewUserEvent(String email, EventType type) {
        super(email);
        this.type = type;
        this.email = email;
    }
}
