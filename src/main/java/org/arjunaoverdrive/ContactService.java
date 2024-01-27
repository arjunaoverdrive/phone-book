package org.arjunaoverdrive;

import org.arjunaoverdrive.util.ContactValidator;
import org.arjunaoverdrive.util.ContactWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Scanner;

@Component
public class ContactService {

    private final ContactBook book;
    private final ContactValidator validator;
    private final ContactWriter contactWriter;

    private final Scanner scanner;

    public ContactService(ContactBook book, ContactValidator validator, ContactWriter contactWriter) {
        this.book = book;
        this.validator = validator;
        this.contactWriter = contactWriter;
        this.scanner = new Scanner(System.in);
    }

    public void listContacts() {
        if (this.book.getContacts().isEmpty()) {
            System.out.println("No contacts yet.");
            return;
        }
        this.book.getContacts()
                .stream()
                .map(c -> c.toString().replaceAll(";", " | "))
                .forEach(System.out::println);
    }

    public void addContact() {
        while (true) {
            System.out.println("Type contact's name, phoneNumber, and email separated with semicolons or 0 to cancel the command. " +
                    "\nExample: John Doe;+123456789;johndoe@example.com");
            String input = scanner.nextLine();

            if (input.equals("0")) return;

            createContact(input);
            return;
        }
    }

    private void createContact(String input) {
        String[] contactData = input.split(";");
        validator.validateContactData(contactData);

        Contact contact = new Contact(contactData[0].trim(), contactData[1].trim(), contactData[2].trim());
        if (this.book.addContact(contact)) {
            System.out.println("Successfully added the contact to the contact list.\n");
            return;
        }
        overrideContact(contact);
    }

    private void overrideContact(Contact contact) {
        System.out.println(
                MessageFormat.format("Contact list already contains the contact whose email is {0}. Override? Y/N",
                        contact.getEmail()));
        String confirm = scanner.nextLine();

        while (!confirm.equalsIgnoreCase("Y") && !confirm.equalsIgnoreCase("N")) {
            System.out.println("Override? Y/N");
            confirm = scanner.nextLine();
        }

        if (confirm.equalsIgnoreCase("Y")) {
            this.book.removeContact(contact.getEmail());
            this.book.addContact(contact);
            System.out.println("Successfully added the contact to the contact list.\n");
        }
    }


    public void removeContact() {
        while (true) {
            System.out.println("Type email of the contact to remove or 0 to cancel the command.");
            String email = scanner.nextLine().trim();
            if (email.equals("0")) return;

            validator.validateEmail(email);
            this.book.removeContact(email);
            System.out.println("Successfully removed the contact from the contact list.\n");

            return;
        }
    }

    public void flushContacts() throws IOException {
        if (this.book.getContacts().isEmpty()) {
            throw new RuntimeException("No contacts to save.");
        }
        contactWriter.writeContactsToFile(this.book.getContacts());
    }

    public void createContactFromFileInput(String input){
        if(input.isEmpty()){
            System.out.println("Cannot create a contact from an empty line.");
            return;
        }
        String[] contactData = input.split(";");
        try {
            validator.validateContactData(contactData);
        }catch (RuntimeException re){
            System.out.println(re.getMessage());
            return;
        }
        Contact contact = new Contact(contactData[0].trim(), contactData[1].trim(), contactData[2].trim());
        this.book.addContact(contact);
        System.out.println("Successfully added the contact to the contact list.");
    }

}
