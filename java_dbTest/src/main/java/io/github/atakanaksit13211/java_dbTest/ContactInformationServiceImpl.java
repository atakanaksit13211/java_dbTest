package io.github.atakanaksit13211.java_dbTest;

public class ContactInformationServiceImpl implements ContactInformationService{
    private final ContactInformationRepository repository;

    private final ContactInformationModelAssembler assembler;

    public ContactInformationServiceImpl(ContactInformationRepository repository, ContactInformationModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // TODO: implement it
    @Override
    public String sanitizePhoneNumber(String input) {
        return input;
    }

    // TODO: implement it
    @Override
    public String sanitizeEMailAddress(String input) {
        return input;
    }
}
