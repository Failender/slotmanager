package de.failender.opt.slotmanager.rest.discord;

import de.failender.opt.slotmanager.discord.OptDiscord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscordController {

    private final OptDiscord discord;

    public DiscordController(OptDiscord discord) {
        this.discord = discord;
    }

    @GetMapping(DiscordAPI.DISCORD)
    public String getTest() {
        return null;
    }
}
