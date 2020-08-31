package de.failender.opt.slotmanager.discord;


import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.EmitterProcessor;

import java.util.List;
import java.util.function.Consumer;

@Component
public class OptDiscord extends ListenerAdapter {


    private final JDA jda;
    private Guild guild;


    private static final Logger LOGGER = LoggerFactory.getLogger(OptDiscord.class);

    public final EmitterProcessor<PrivateMessageReceivedEvent> privateMessageReceivedEvent = EmitterProcessor.create();

    public OptDiscord(DiscordConfiguration discordConfiguration) {


        if (discordConfiguration.getToken() == null) {
            LOGGER.error("No discord token configured, use opt.slotmanager.discord.token");
            jda = null;
            guild = null;
            return;
        }
        try {
            System.out.println("Connecting to Discord..");
            jda = new JDABuilder(discordConfiguration.getToken())
                    .addEventListener(this)
                    .build();
            jda.awaitReady();
            Guild guild = jda.getGuildsByName(discordConfiguration.getGuild(), true).get(0);
            List<Member> members = guild.getMembers();
            System.out.println(members);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {

        privateMessageReceivedEvent.onNext(event);
    }

    public void messageUser(String message, String userTag) {
        messageUser(message, userTag, null);
    }

    public void messageUser(String message, String userId, Consumer<Message> messageConsumer) {
        User user = jda.getUserById(userId);
        if (user == null) {
            LOGGER.error("Cant find user with id {} wont send message {}", user, message);
            return;
        }
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue(re -> {

            if (messageConsumer != null) {
                messageConsumer.accept(re);
            }

        }));
    }

    public JDA getJda() {
        return jda;
    }
}
