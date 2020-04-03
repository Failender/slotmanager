package de.failender.opt.slotmanager.persistance.event;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<EventEntity, Long> {

    List<EventEntity> findAllByDateAfter(LocalDateTime localDateTime);
    List<EventEntity> findAll();
    Optional<EventEntity> findByName(String name);
}
