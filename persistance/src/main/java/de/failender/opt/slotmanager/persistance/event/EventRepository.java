package de.failender.opt.slotmanager.persistance.event;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
}
