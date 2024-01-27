package org.arjunaoverdrive.config;

import org.arjunaoverdrive.ContactService;
import org.arjunaoverdrive.util.ContactsInitializer;
import org.arjunaoverdrive.util.IOFileInitializer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
@ComponentScan("org.arjunaoverdrive")
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitAppConfig {

    private final ContactService service;
    private final IOFileInitializer fileInitializer;

    public InitAppConfig(ContactService service, IOFileInitializer fileInitializer) {
        this.service = service;
        this.fileInitializer = fileInitializer;
    }

    @Bean
    public ContactsInitializer contactsInitializer(){
        return new ContactsInitializer(service, fileInitializer);
    }

    @Bean
    public void initContacts() {
        try {
            contactsInitializer().initContacts();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
