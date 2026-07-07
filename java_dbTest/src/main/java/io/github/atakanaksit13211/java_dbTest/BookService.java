package io.github.atakanaksit13211.java_dbTest;

public interface BookService {
    boolean isBorrowed(Book book);

    Borrowing getCurrentBorrowing(Book book);

}
