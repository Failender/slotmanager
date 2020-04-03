package de.failender.opt.slotmanager.persistance.event;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserToEventRepository extends CrudRepository<UserToEventEntity, UserToEventEntity.UserToEventId> {

    List<UserToEventEntity> findByEventId(Long id);
    void deleteByEventIdAndUserId(Long eventId, Long userId);
}
