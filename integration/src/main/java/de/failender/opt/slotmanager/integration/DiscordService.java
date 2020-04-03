package de.failender.opt.slotmanager.integration;

import de.failender.opt.slotmanager.discord.OptDiscord;
import de.failender.opt.slotmanager.persistance.event.EventEntity;
import de.failender.opt.slotmanager.persistance.event.EventRepository;
import de.failender.opt.slotmanager.persistance.event.UserToEventEntity;
import de.failender.opt.slotmanager.persistance.event.UserToEventRepository;
import de.failender.opt.slotmanager.persistance.user.UserEntity;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscordService {

    private final OptDiscord discord;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final UserToEventRepository userToEventRepository;
    private final EventService eventService;


    public DiscordService(OptDiscord discord, UserRepository userRepository, EventRepository eventRepository, UserToEventRepository userToEventRepository, EventService eventService) {
        this.discord = discord;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.userToEventRepository = userToEventRepository;
        this.eventService = eventService;

        discord.privateMessageReceivedEvent.subscribe(event -> {
            handlePrivateMessage(event);

        });
    }

    public void messageAllUser() {

        List<UserEntity> users = userRepository.findByDiscordIdIsNotNull();
        List<EventEntity> pendingEvents = eventRepository.findAllByDateAfter(LocalDateTime.now());
        for (EventEntity pendingEvent : pendingEvents) {
            processEvent(pendingEvent, users);

        }



    }

    private void processEvent(EventEntity eventEntity, List<UserEntity> users ) {
        List<UserToEventEntity> userToEvents = userToEventRepository.findByEventId(eventEntity.getId());
        users
                .stream()
                .filter(user -> userToEvents
                        .stream()
                        .map(userToEventEntity -> userToEventEntity.getUserId() == user.getId())
                        .findFirst()
                        .map(data -> false)
                        .orElse(true))
                .forEach(user -> messageUserAboutPendingEvent(user, eventEntity));
    }

    private void messageUserAboutPendingEvent(UserEntity userEntity, EventEntity eventEntity) {
        discord.messageUser("Du hast dich bisher noch nicht für das Event \"" + eventEntity.getName() + "\" eingetragen" + " bitte nutze den Befehl \"!attendEvent|!declineEvent|!maybeEvent " + eventEntity.getName() + "\" um an dem Event teilzunehmen", userEntity.getDiscordId());
    }

    private void handlePrivateMessage(PrivateMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if(event.getAuthor().isBot()) {
            return;
        }
        if(!message.startsWith("!")) {
            discord.messageUser("Befehl wurde nicht erkannt", event.getAuthor().getId());
            return;
        }
        if(message.startsWith("!register")) {
            discord.messageUser("Registrierung ist noch nicht implementiert", event.getAuthor().getId());
        }

        String discordId = event.getAuthor().getId();
        Optional <UserEntity> userEntityOptional = userRepository.findByDiscordId(discordId);
        if(!userEntityOptional.isPresent()) {
            discord.messageUser("Du bist bisher noch nicht registriert", discordId);
            return;
        }
        UserEntity userEntity = userEntityOptional.get();
        if(message.startsWith("!attendEvent")) {
            String eventName = message.substring("!attendEvent ".length());
            Optional<EventEntity> eventEntityOptional = eventRepository.findByName(eventName);
            if(!eventEntityOptional.isPresent()) {
                discord.messageUser("Das Event " + eventName + " konnte nicht gefunden werden", discordId);
                return;
            }
            eventService.registerUserForEvent(userEntity, eventEntityOptional.get(), UserToEventEntity.State.APPROVED);
            discord.messageUser("Du nimmst jetzt an dem Event Teil", discordId);
            return;
        }

        if(message.startsWith("!declineEvent")) {
            String eventName = message.substring("!declineEvent ".length());
            Optional<EventEntity> eventEntityOptional = eventRepository.findByName(eventName);
            if(!eventEntityOptional.isPresent()) {
                discord.messageUser("Das Event " + eventName + " konnte nicht gefunden werden", discordId);
                return;
            }
            eventService.registerUserForEvent(userEntity, eventEntityOptional.get(), UserToEventEntity.State.DECLINED);
            discord.messageUser("Du nimmst nicht an dem Event Teil", discordId);
            return;
        }

        if(message.startsWith("!maybeEvent")) {
            String eventName = message.substring("!maybeEvent ".length());
            Optional<EventEntity> eventEntityOptional = eventRepository.findByName(eventName);
            if(!eventEntityOptional.isPresent()) {
                discord.messageUser("Das Event " + eventName + " konnte nicht gefunden werden", discordId);
                return;
            }
            eventService.registerUserForEvent(userEntity, eventEntityOptional.get(), UserToEventEntity.State.MAYBE);
            discord.messageUser("Du nimmst vielleicht an dem Event Teil", discordId);
            return;
        }

        discord.messageUser("Der Befehl konnte nicht erkannt werden", discordId);




    }

}
