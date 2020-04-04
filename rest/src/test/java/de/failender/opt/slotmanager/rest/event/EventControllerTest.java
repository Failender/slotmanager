package de.failender.opt.slotmanager.rest.event;

import de.failender.opt.slotmanager.persistance.event.EventEntity;
import de.failender.opt.slotmanager.persistance.event.EventRepository;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import de.failender.opt.slotmanager.rest.SlotmanagerApplicationTests;
import org.assertj.core.api.Assertions;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriTemplate;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


public class EventControllerTest extends SlotmanagerApplicationTests {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventRestService eventRestService;


    @FlywayTest
    @Test
    public void testGetEvents() throws Exception {
        authenticationService.authenticate(ADMIN, ADMIN);
        EventDto eventDto = new EventDto();
        eventDto.setName("DEMO");
        eventDto.setDate(LocalDateTime.now());
        eventRestService.createEvent(eventDto);

        mvc.perform(MockMvcRequestBuilders.get(EventAPI.EVENTS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("DEMO")))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @FlywayTest
    @Test
    public void testGetEvent() throws Exception {
        authenticationService.authenticate(ADMIN, ADMIN);
        EventDto eventDto = new EventDto();
        eventDto.setName("DEMO");
        eventDto.setDate(LocalDateTime.now());
        long id = eventRestService.createEvent(eventDto);

        String uri = new UriTemplate(EventAPI.EVENT).expand(Collections.singletonMap("id", id)).toString();
        mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("DEMO")));
    }

    @FlywayTest
    @Test
    public void testGetEventNotFound() throws Exception {


        String uri = new UriTemplate(EventAPI.EVENT).expand(Collections.singletonMap("id", 1)).toString();
        mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @FlywayTest
    @Test
    public void testCreateEventForbidden() throws Exception {

        EventDto eventDto = new EventDto();
        eventDto.setName("DEMO");
        eventDto.setDate(LocalDateTime.now());

        String body = springMvcJacksonConverter.getObjectMapper().writeValueAsString(eventDto);

        mvc.perform(MockMvcRequestBuilders.post(EventAPI.EVENTS)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @FlywayTest
    @Test
    public void testCreateEvent() throws Exception {

        EventDto eventDto = new EventDto();
        eventDto.setName("DEMO");
        eventDto.setDate(LocalDateTime.now());

        mvc.perform(MockMvcRequestBuilders.post(EventAPI.EVENTS)
                .content(springMvcJacksonConverter.getObjectMapper().writeValueAsString(eventDto))
                .headers(adminHeaders())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()));

        Assertions.assertThat(eventRestService.getEvents().size()).isEqualTo(1);
    }










}
