package de.failender.opt.slotmanager.integration;

import de.failender.opt.slotmanager.persistance.event.EventEntity;
import de.failender.opt.slotmanager.persistance.event.UserToEventEntity;
import de.failender.opt.slotmanager.persistance.event.UserToEventRepository;
import de.failender.opt.slotmanager.persistance.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class EventService {


    private final UserToEventRepository userToEventRepository;

    public EventService(UserToEventRepository userToEventRepository) {
        this.userToEventRepository = userToEventRepository;
    }

    public void registerUserForEvent(UserEntity userEntity, EventEntity eventEntity, UserToEventEntity.State state) {

        //Delete already existing status if it exists
        userToEventRepository.deleteByEventIdAndUserId(eventEntity.getId(), userEntity.getId());

        UserToEventEntity userToEventEntity = new UserToEventEntity();
        userToEventEntity.setEventId(eventEntity.getId());
        userToEventEntity.setUserId(userEntity.getId());
        userToEventEntity.setState(state);
        userToEventRepository.save(userToEventEntity);

    }
}
