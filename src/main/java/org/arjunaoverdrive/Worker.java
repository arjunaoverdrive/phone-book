package org.arjunaoverdrive;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class Worker {

    private final ContactService service;

    public Worker(ContactService service) {
        this.service = service;
    }

    public void start() throws IOException {
        while (true) {
            System.out.println("""
                    \nType number to run a command:
                    1 - list contacts.
                    2 - add a new contact.
                    3 - remove a contact.
                    4 - save contacts to the file.""");
            Scanner scanner = new Scanner(System.in);
            String action = scanner.nextLine();
            try {
                switch (action) {
                    case "1" -> service.listContacts();
                    case "2" -> service.addContact();
                    case "3" -> service.removeContact();
                    case "4" -> service.flushContacts();
                    default -> System.out.println("Please type the command number.");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
