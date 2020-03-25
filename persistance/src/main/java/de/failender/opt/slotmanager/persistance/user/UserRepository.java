package de.failender.opt.slotmanager.persistance.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByName(String name);

    @Query(nativeQuery = true, value = "SELECT RIGHTS.NAME FROM USERS U INNER JOIN ROLES_TO_USER RTU ON RTU.USER_ID = U.ID " +
            "INNER JOIN ROLES_TO_RIGHTS RTR ON RTR.ROLE_ID = RTU.ROLE_ID INNER JOIN RIGHTS ON RIGHTS.ID = RTR.RIGHT_ID WHERE U.ID = ?1")
    List<String> getUserRights(long userid);

    Optional<UserEntity> findById(Long id);

}
