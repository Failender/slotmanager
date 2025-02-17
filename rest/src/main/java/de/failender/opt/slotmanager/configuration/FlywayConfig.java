package de.failender.opt.slotmanager.configuration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.migration.JavaMigration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    private final JavaMigration[] javaMigrations;

    private Flyway flyway;


    public FlywayConfig(JavaMigration[] javaMigrations) {
        this.javaMigrations = javaMigrations;
    }

    @Bean
    public Flyway flyway(DataSource theDataSource, @Value("${opt.slotmanager.datasource.clean.on.start:false}") boolean cleanOnStart) {
        flyway = Flyway.configure().locations("classpath:db/migration")
                .javaMigrations(javaMigrations)
                .dataSource(theDataSource).load();
        if (cleanOnStart) {
            flyway.clean();
        }
        return flyway;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        flyway.migrate();
    }
}
