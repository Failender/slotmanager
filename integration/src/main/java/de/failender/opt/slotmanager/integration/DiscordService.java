package de.failender.opt.slotmanager.integration;

import de.failender.opt.slotmanager.discord.OptDiscord;
import org.springframework.stereotype.Service;

@Service
public class DiscordService {

    private final OptDiscord discord;

    public DiscordService(OptDiscord discord) {
        this.discord = discord;
    }

    public void messageAllUser() {

    }
}
