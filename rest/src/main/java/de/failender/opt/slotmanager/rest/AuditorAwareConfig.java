package de.failender.opt.slotmanager.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareConfig {

    @Bean
    public SecurityAuditorAware auditorAware() {
        return new SecurityAuditorAware();
    }
}
