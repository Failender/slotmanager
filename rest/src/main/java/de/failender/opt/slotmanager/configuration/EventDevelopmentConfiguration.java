package de.failender.opt.slotmanager.configuration;

import de.failender.opt.slotmanager.integration.DiscordService;
import de.failender.opt.slotmanager.migration.SetupConfiguration;
import de.failender.opt.slotmanager.persistance.event.EventEntity;
import de.failender.opt.slotmanager.persistance.event.EventRepository;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDateTime;

@Configuration
@Profile("dev")
@DependsOn()
public class EventDevelopmentConfiguration implements ApplicationListener<ContextRefreshedEvent> {


    private final EventRepository eventRepository;
    private final SetupConfiguration setupConfiguration;
    private final UserRepository userRepository;
    private final DiscordService discordService;


    public EventDevelopmentConfiguration(EventRepository eventRepository, SetupConfiguration setupConfiguration, UserRepository userRepository, DiscordService discordService) {
        this.eventRepository = eventRepository;
        this.setupConfiguration = setupConfiguration;
        this.userRepository = userRepository;
        this.discordService = discordService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setName("Test-Event");
        eventEntity.setCreatedDate(LocalDateTime.now());
        eventEntity.setCreatedBy(userRepository.findByName(setupConfiguration.getName()).getId());
        eventEntity.setDate(LocalDateTime.now().plusDays(2));
        eventRepository.save(eventEntity);

        discordService.messageAllUser();
    }
}
