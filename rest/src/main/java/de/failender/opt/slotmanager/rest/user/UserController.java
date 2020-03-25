package de.failender.opt.slotmanager.rest.user;

import de.failender.opt.slotmanager.persistance.user.UserEntity;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping(UserAPI.API_USERS)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(UserAPI.API_USERS)
    public Long createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping(UserAPI.API_USER)
    public void updateUser(Long user, UserDto userDto) {
        userService.updateUser(user, userDto);

    }


}
