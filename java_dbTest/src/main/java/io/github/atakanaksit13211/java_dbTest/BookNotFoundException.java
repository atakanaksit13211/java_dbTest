package io.github.atakanaksit13211.java_dbTest;

class BookNotFoundException extends RuntimeException{

    BookNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
