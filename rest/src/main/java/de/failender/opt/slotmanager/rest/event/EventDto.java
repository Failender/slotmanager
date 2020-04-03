package de.failender.opt.slotmanager.rest.event;

import de.failender.opt.slotmanager.persistance.event.EventEntity;

import java.time.LocalDateTime;

public class EventDto {

    private Long id;
    private String name;
    private LocalDateTime date;

    public EventDto(EventEntity event) {
        name = event.getName();
        date = event.getDate();
        id = event.getId();
    }

    public EventDto() {

    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }
}
