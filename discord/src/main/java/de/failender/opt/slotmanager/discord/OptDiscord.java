package de.failender.opt.slotmanager.discord;


import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public class OptDiscord extends ListenerAdapter {


    private final JDA jda;
    public OptDiscord(
            @Value("${opt.slotmanager.discord.token}")String token) {


        JDA jda = null;

        try {
            System.out.println("Connecting to Discord..");
            jda = new JDABuilder(token)

                    .addEventListener(this)
                    .build().awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        this.jda = jda;

    }

    public JDA getJda() {
        return jda;
    }
}
