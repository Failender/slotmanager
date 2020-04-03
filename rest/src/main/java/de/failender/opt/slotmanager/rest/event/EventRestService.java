package de.failender.opt.slotmanager.rest.event;

import de.failender.opt.slotmanager.persistance.event.EventEntity;
import de.failender.opt.slotmanager.persistance.event.EventRepository;
import de.failender.opt.slotmanager.persistance.event.UserToEventEntity;
import de.failender.opt.slotmanager.persistance.event.UserToEventRepository;
import de.failender.opt.slotmanager.persistance.user.UserEntity;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import de.failender.opt.slotmanager.rest.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventRestService {

    private final EventRepository eventRepository;
    private final UserToEventRepository userToEventRepository;
    private final UserRepository userRepository;

    public EventRestService(EventRepository eventRepository, UserToEventRepository userToEventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userToEventRepository = userToEventRepository;
        this.userRepository = userRepository;
    }

    public List<EventDto> getEvents() {
        return eventRepository.findAll()
                .stream()
                .map(EventDto::new)
                .collect(Collectors.toList());

    }

    public EventDto getEvent(Long id) {
        return eventRepository.findById(id)
                .map(EventDto::new)
                .orElseThrow(() -> new NotFoundException());
    }

    public Long createEvent(EventDto eventDto) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(eventDto.getName());
        eventEntity.setDate(eventDto.getDate());
        eventRepository.save(eventEntity);
        return eventEntity.getId();
    }


    public List<EventMemberDto> getMembers(Long id) {
        if(!eventRepository.existsById(id)) {
            throw new NotFoundException();
        }
        List<UserToEventEntity> userToEventEntities =  userToEventRepository.findByEventId(id);
        Map<Long, UserEntity> users = userRepository.findAllById(userToEventEntities.stream().map(UserToEventEntity::getUserId).collect(Collectors.toList())).stream().collect(Collectors.toMap(UserEntity::getId, user -> user));
        return userToEventEntities.stream().map(entry -> {
            EventMemberDto dto = new EventMemberDto();
            dto.setName(users.get(entry.getUserId()).getName());
            dto.setState(entry.getState());
            return dto;
        }).collect(Collectors.toList());

    }
}
