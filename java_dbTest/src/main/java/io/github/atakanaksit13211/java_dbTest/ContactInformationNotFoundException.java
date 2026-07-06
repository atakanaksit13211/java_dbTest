package io.github.atakanaksit13211.java_dbTest;

public class ContactInformationNotFoundException extends RuntimeException{

    public ContactInformationNotFoundException(Long id) {
        super("Could not find contact information " + id);
    }
}
