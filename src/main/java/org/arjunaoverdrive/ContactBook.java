package org.arjunaoverdrive;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

@Component
public class ContactBook {
    private Set<Contact> contacts;

    public ContactBook() {
        this.contacts = new HashSet<>();
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts){
        this.contacts = contacts;
    }

    public boolean addContact(Contact contact) {
        if(contacts.contains(contact)){
            return false;
        }
        return contacts.add(contact);
    }

    public void removeContact(String email){
        Contact contact;
        try {
            contact = this.contacts
                    .stream()
                    .filter(c -> c.getEmail().equals(email))
                    .findFirst().get();
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("Contact with email {0} not found.", email));
        }
        removeContact(contact);
    }

    private void removeContact(Contact contact){contacts.remove(contact);}


}
