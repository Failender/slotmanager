package de.failender.opt.slotmanager.rest;

import de.failender.opt.slotmanager.SlotmanagerApplication;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import de.failender.opt.slotmanager.rest.security.AuthenticationService;
import net.bytebuddy.asm.Advice;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SlotmanagerApplication.class)
@ActiveProfiles(profiles="test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ContextConfiguration(initializers = {SlotmanagerApplicationTests.Initializer.class})
@AutoConfigureMockMvc
public class SlotmanagerApplicationTests {

    public static final String ADMIN = "Admin";

    @Autowired
    protected AuthenticationService authenticationService;

    @Autowired
    protected MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("opt")
            .withUsername("postgres")
            .withPassword("postgres");


    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    protected HttpHeaders adminHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", ADMIN+";" +ADMIN);
        return headers;
    }


}
