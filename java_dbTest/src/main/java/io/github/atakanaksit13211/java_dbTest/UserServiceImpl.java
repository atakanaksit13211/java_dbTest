package io.github.atakanaksit13211.java_dbTest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/* TODO: rename this class as impl is an anti pattern. Maybe UserServiceV1 or UserServiceRegular? */
public class UserServiceImpl implements UserService {

    final UserRepository repository;

    final UserModelAssembler assembler;

    public UserServiceImpl(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public List<Borrowing> getDueBooks(User user) { //list of books that are past the due date
        if(user.isBorrowingsEmpty()){
            return Collections.emptyList();
        }

        return user.getBorrowings().stream()
                .filter(borrowing -> borrowing.getDue_timestamp().isBefore(ZonedDateTime.now())) //due time is before (past) current time
                .collect(Collectors.toCollection(List::of));
    }

    public boolean isThereDueBooks(User user){
        if(user.isBorrowingsEmpty()){
            return false;
        }

        return user.getBorrowings().stream().anyMatch(borrowing -> borrowing.getDue_timestamp().isBefore(ZonedDateTime.now()));
    }

    public CollectionModel<EntityModel<Borrowing>> getBorrowings(Long id) {

        BorrowingModelAssembler tmpAssembler = new BorrowingModelAssembler();

        List<EntityModel<Borrowing>> borrowings = (repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id))
                .getBorrowings()
                .stream()
                .map(tmpAssembler::toModel)
                .collect(Collectors.toList())
        );

        return CollectionModel.of(borrowings, //
                linkTo(methodOn(UserController.class).all()).withSelfRel()

        );
    }




}
