package de.failender.opt.slotmanager.rest.event;

import de.failender.opt.slotmanager.persistance.event.UserToEventEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {


    private final EventRestService eventRestService;

    public EventController(EventRestService eventRestService) {
        this.eventRestService = eventRestService;
    }

    @GetMapping(EventAPI.EVENTS)
    public List<EventDto> getEvents() {
        return eventRestService.getEvents();

    }

    @GetMapping(EventAPI.EVENT)
    public EventDto getEvent(@PathVariable Long id) {
        return eventRestService.getEvent(id);
    }

    @PostMapping(EventAPI.EVENTS)
    public Long createEvent(@RequestBody EventDto eventDto) {
        return eventRestService.createEvent(eventDto);
    }


    @GetMapping(EventAPI.EVENT_MEMBER)
    public List<EventMemberDto> getMembers(@PathVariable Long id) {
        return eventRestService.getMembers(id);
    }

    @PostMapping(EventAPI.EVENT_STATE)
    public void updateStatusForEvent(@PathVariable Long id, @PathVariable UserToEventEntity.State state) {
        eventRestService.updateStatusForEvent(id, state);
    }




}
