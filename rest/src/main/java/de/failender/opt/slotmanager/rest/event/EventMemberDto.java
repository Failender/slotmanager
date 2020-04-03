package de.failender.opt.slotmanager.rest.event;

import de.failender.opt.slotmanager.persistance.event.UserToEventEntity;

public class EventMemberDto {

    private String name;
    private UserToEventEntity.State state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserToEventEntity.State getState() {
        return state;
    }

    public void setState(UserToEventEntity.State state) {
        this.state = state;
    }
}
