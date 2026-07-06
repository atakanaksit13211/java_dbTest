package io.github.atakanaksit13211.java_dbTest;

public class BorrowingNotFoundException extends RuntimeException{

    BorrowingNotFoundException(Long id) {
        super("Could not find borrowing " + id);
    }

}
