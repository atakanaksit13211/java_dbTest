package io.github.atakanaksit13211.java_dbTest;

public class AddressServiceImpl implements AddressService{
    private final AddressRepository repository;

    private final AddressModelAssembler assembler;

    public AddressServiceImpl(AddressRepository repository, AddressModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
}
