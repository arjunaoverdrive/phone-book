package org.arjunaoverdrive.util;

import org.arjunaoverdrive.ContactService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class ContactsInitializer {

    private final ContactService service;
    private final IOFileInitializer fileInitializer;


    public ContactsInitializer(ContactService service, IOFileInitializer fileInitializer) {
        this.service = service;
        this.fileInitializer = fileInitializer;
    }

    @Value("${app.init.file}")
    private String initFilePath;


    public void initContacts() throws IOException {
        System.out.println("Reading contacts from file ...");
        File file = fileInitializer.getFile(initFilePath);
        if (file == null) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                service.createContactFromFileInput(line);
            }
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}
