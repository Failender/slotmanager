package de.failender.opt.slotmanager.rest.steam;

import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import de.failender.opt.slotmanager.integration.steam.SteamOpenIDService;
import de.failender.opt.slotmanager.integration.steam.SteamRestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SteamController {

    private final SteamOpenIDService steamOpenIDService;
    private final SteamRestService steamRestService;

    public SteamController(SteamOpenIDService steamOpenIDService, SteamRestService steamRestService) {
        this.steamOpenIDService = steamOpenIDService;
        this.steamRestService = steamRestService;
    }

    @GetMapping(SteamAPI.STEAM_AUTHENTICATE)
    public String getSteamAuthenticationLink() {
        return steamOpenIDService.login("http://localhost:8080/index.html");
    }

    @PostMapping(SteamAPI.STEAM_VERIFY)
    public Player verify(@RequestBody Map<String, String> params) {
        String result = steamOpenIDService.verify("http://localhost:8080/index.html", params);
        if (result != null) {
            return steamRestService.test(result);

        }
        return null;
    }
}
