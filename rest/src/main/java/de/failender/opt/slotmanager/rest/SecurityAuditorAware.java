package de.failender.opt.slotmanager.rest;

import de.failender.opt.slotmanager.persistance.user.UserEntity;
import de.failender.opt.slotmanager.rest.exceptions.AuthenticationMissingException;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

       return getCurrentUserOptional().map(UserEntity::getId);
    }

    public static Optional<UserEntity> getCurrentUserOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        if(authentication.getPrincipal() instanceof String) {

        }

        return Optional.of(((UserEntity) authentication.getPrincipal()));
    }

    public static UserEntity getCurrentUser() {
        return getCurrentUserOptional().orElseThrow(() -> new AuthenticationMissingException());
    }
}
