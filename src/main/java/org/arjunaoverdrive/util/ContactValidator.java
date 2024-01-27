package org.arjunaoverdrive.util;

import org.springframework.stereotype.Component;

@Component
public class ContactValidator {

    public boolean validateContactData(String[] data) {
        return validateParamsCount(data) && validateName(data[0].trim()) && validatePhoneNumber(data[1].trim()) &&
                validateEmail(data[2].trim());
    }

    public boolean validateEmail(String email) {
        if (!email.matches(".*?@\\w*?\\.\\w{2,4}")) {
            throw new RuntimeException("Invalid email format. Please make sure the email is correct.");
        }
        return true;
    }

    private boolean validatePhoneNumber(String number) {
        if (!number.matches("\\+\\d{11}")) {
            throw new RuntimeException("Invalid phone format. Please make sure the phone number follows the pattern: +1234567890.");
        }
        return true;
    }

    private boolean validateName(String name) {
        if (!name.matches("(\\w*?\\s?)*?")) {
            throw new RuntimeException("Invalid name format. Please make sure contact's name consists of letters only.");
        }
        return true;
    }

    private boolean validateParamsCount(String[] data) {
        if (data.length != 3) {
            throw new RuntimeException("Invalid contact data. Please insert contact's name, phone number, and email");
        }
        return true;
    }
}
