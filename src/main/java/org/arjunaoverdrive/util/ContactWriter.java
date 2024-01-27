package org.arjunaoverdrive.util;

import org.arjunaoverdrive.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

@Component
public class ContactWriter {

    private final IOFileInitializer fileInitializer;

    @Value("${app.storage.file}")
    private String savedContactsFilePath;

    public ContactWriter(IOFileInitializer fileInitializer) {
        this.fileInitializer = fileInitializer;
    }


    public void writeContactsToFile(Set<Contact> contacts) throws IOException {

        File file = fileInitializer.getFile(savedContactsFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Contact c : contacts) {
                writer.write(c.toString() + "\n");
            }
            writer.flush();
            System.out.println("Contacts saved to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
