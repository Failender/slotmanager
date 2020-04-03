package de.failender.opt.slotmanager.rest.user;

import de.failender.opt.slotmanager.persistance.user.UserEntity;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRestService {


    private final UserRepository userRepository;

    public UserRestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    public Long createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(userDto.getPassword());
        userEntity.setName(userDto.getName());
        return userRepository.save(userEntity).getId();
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    public void updateUser(Long user, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(user).orElseThrow(() -> new EntityNotFoundException());
        if(userDto.getDiscordId() != null) {
            userEntity.setDiscordId(userDto.getDiscordId());
        }
        if(userDto.getTeamspeakId() != null) {
            userEntity.setTeamspeakId(userDto.getTeamspeakId());
        }
    }
}
