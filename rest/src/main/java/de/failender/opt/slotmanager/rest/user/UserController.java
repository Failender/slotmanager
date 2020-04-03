package de.failender.opt.slotmanager.rest.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    private final UserRestService userRestService;

    public UserController(UserRestService userRestService) {
        this.userRestService = userRestService;
    }

    @GetMapping(UserAPI.API_USERS)
    public List<UserDto> getAllUsers() {
        return userRestService.getAllUsers();
    }

    @PostMapping(UserAPI.API_USERS)
    public Long createUser(UserDto userDto) {
        return userRestService.createUser(userDto);
    }

    @PutMapping(UserAPI.API_USER)
    public void updateUser(Long user, UserDto userDto) {
        userRestService.updateUser(user, userDto);

    }


}
