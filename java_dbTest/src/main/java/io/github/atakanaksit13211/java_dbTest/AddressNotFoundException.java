package io.github.atakanaksit13211.java_dbTest;

public class AddressNotFoundException extends RuntimeException {

    AddressNotFoundException(Long id) {
            super("Could not find address " + id);
        }

}
