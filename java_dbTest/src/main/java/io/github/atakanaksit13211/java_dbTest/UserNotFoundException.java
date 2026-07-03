package io.github.atakanaksit13211.java_dbTest;

class UserNotFoundException extends RuntimeException {

    UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
