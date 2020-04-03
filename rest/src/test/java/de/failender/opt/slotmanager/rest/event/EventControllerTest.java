package de.failender.opt.slotmanager.rest.event;

import de.failender.opt.slotmanager.SlotmanagerApplication;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import de.failender.opt.slotmanager.rest.SlotmanagerApplicationTests;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EventControllerTest extends SlotmanagerApplicationTests {

    @Before
    public void setup() {
        System.out.println("SET");
    }

    @Autowired
    private UserRepository userRepository;


    @FlywayTest
    @Test
    public void doTest() {
        System.out.println(userRepository.findAll());

    }


}
