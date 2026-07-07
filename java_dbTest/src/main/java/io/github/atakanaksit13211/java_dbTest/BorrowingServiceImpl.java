package io.github.atakanaksit13211.java_dbTest;

public class BorrowingServiceImpl implements BorrowingService{
    private final BorrowingRepository repository;

    private final BorrowingModelAssembler assembler;

    public BorrowingServiceImpl(BorrowingRepository repository, BorrowingModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
}
