package de.failender.opt.slotmanager.rest.steam;

import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import de.failender.opt.slotmanager.integration.steam.SteamOpenIDService;
import de.failender.opt.slotmanager.integration.steam.SteamRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SteamController {

    private final SteamOpenIDService steamOpenIDService;
    private final SteamRestService steamRestService;
    private final String redirectUri;

    public SteamController(SteamOpenIDService steamOpenIDService, SteamRestService steamRestService, @Value("${opt.slotmanager.steam.redirect.uri:http://localhost:8080/index.html}") String redirectUri) {
        this.steamOpenIDService = steamOpenIDService;
        this.steamRestService = steamRestService;
        this.redirectUri = redirectUri;
    }

    @GetMapping(SteamAPI.STEAM_AUTHENTICATE)
    public String getSteamAuthenticationLink() {
        return steamOpenIDService.login(redirectUri);
    }

    @PostMapping(SteamAPI.STEAM_VERIFY)
    public Player verify(@RequestBody Map<String, String> params) {
        String result = steamOpenIDService.verify(redirectUri, params);
        if (result != null) {
            return steamRestService.test(result);

        }
        return null;
    }
}
