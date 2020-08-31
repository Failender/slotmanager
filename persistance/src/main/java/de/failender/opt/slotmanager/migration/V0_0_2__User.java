package de.failender.opt.slotmanager.migration;

import de.failender.opt.slotmanager.persistance.user.UserEntity;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class V0_0_2__User extends BaseJavaMigration {

    @Autowired
    @Lazy
    private UserRepository userRepository;
    private final SetupConfiguration setupConfiguration;

    public V0_0_2__User(SetupConfiguration setupConfiguration) {
        this.setupConfiguration = setupConfiguration;
    }

    @Override
    public void migrate(Context context) {

        UserEntity userEntity = new UserEntity();
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setName(setupConfiguration.getName());
        userEntity.setPassword(setupConfiguration.getPassword());
        userEntity.setDiscordId(setupConfiguration.getDiscordId());
        userRepository.save(userEntity);
    }

}
