package de.failender.opt.slotmanager.discord;


import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.EmitterProcessor;

import javax.security.auth.login.LoginException;

@Component
public class OptDiscord extends ListenerAdapter {


    private final JDA jda;
    private final Guild guild;

    public final EmitterProcessor<PrivateMessageReceivedEvent> privateMessageReceivedEvent = EmitterProcessor.create();

    public OptDiscord(DiscordConfiguration discordConfiguration) {


        try {
            System.out.println("Connecting to Discord..");
            jda = new JDABuilder(discordConfiguration.getToken())

                    .addEventListener(this)
                    .build().awaitReady();

            guild = jda.getGuildsByName(discordConfiguration.getGuild(), true).get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        privateMessageReceivedEvent.onNext(event);
    }

    public void messageUser(String message, String userId) {
        jda.getUserById(userId).openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }
}
