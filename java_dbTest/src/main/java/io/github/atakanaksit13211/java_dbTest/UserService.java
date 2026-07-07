package io.github.atakanaksit13211.java_dbTest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
we should probably only keep business logic here
(so things that the controllers will use that are related to core logic like getting due books and other operations that are
not just getting values)
 */
public interface UserService {


    List<Borrowing> getDueBooks(User user);
    boolean isThereDueBooks(User user);

    CollectionModel<EntityModel<Borrowing>> getBorrowings(Long id);

    EntityModel<Address> getAddress(Long id);

    EntityModel<ContactInformation> getContact(Long id);

}
