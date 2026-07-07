package io.github.atakanaksit13211.java_dbTest;

import java.util.Optional;

/* TODO: rename this class as impl is an anti pattern. Maybe BookServiceV1 or BookServiceRegular? */
public class BookServiceImpl implements BookService{
    final BookRepository repository;

    final BookModelAssembler assembler;

    public BookServiceImpl(BookRepository repository, BookModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public boolean isBorrowed(Book book) {
        if(book.getBorrowing() == null){ //no one borrowed this book ever so it's null
            return false;
        }
        return book.getBorrowing().stream().anyMatch(borrowing -> !borrowing.isReturned());
    }

    @Override
    public Borrowing getCurrentBorrowing(Book book) {
        if (!isBorrowed(book)) { // no one borrowed it
            return null;
        }
        if( book.getBorrowing().stream().filter(borrowing -> !borrowing.isReturned()).count() > 1 ){ //WE HAVE MULTIPLE BORROWINGS!!!!
            throw new RuntimeException("Multiple people have borrowed this book! cannot continue");
        }
        Optional<Borrowing> tmp = book.getBorrowing().stream().filter(borrowing -> !borrowing.isReturned() ).findAny();
        if(tmp.isPresent()){
            return tmp.get();
        }
        return null; //we should never reach here
    }
}
