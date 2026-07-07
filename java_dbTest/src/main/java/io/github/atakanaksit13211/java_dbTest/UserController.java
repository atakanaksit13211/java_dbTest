package io.github.atakanaksit13211.java_dbTest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


// tag::constructor[]
@RestController
class UserController {

    private final UserRepository repository;

    private final UserModelAssembler assembler;

    private final UserService service;

    UserController(UserRepository repository, UserModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
        this.service = new UserServiceImpl(repository, assembler);
    }
    // end::constructor[]

    // Aggregate root

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(users, //
                linkTo(methodOn(UserController.class).all()).withSelfRel(),
                linkTo(methodOn(UserController.class).newUser(new User())).withRel("new")
        );
    }

    @PostMapping("/users/new")
    ResponseEntity<?> newUser(@RequestBody User newUser) {

        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Single item

    @GetMapping("/users/{id}")
    EntityModel<User> one(@PathVariable Long id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    @PutMapping("/users/{id}/update")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        User updatedUser = repository.findById(id) //
                .map(user -> {
                    user.setUser_name(newUser.getUser_name());
                    user.setEmail_address(newUser.getEmail_address());
                    user.setPassword_salt(newUser.getPassword_salt());
                    user.setPassword_hash(newUser.getPassword_hash());
                    return repository.save(user);
                }) //
                .orElseGet(() -> {
                    return repository.save(newUser);
                });

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/users/{id}/borrowing/add")
    ResponseEntity<?> userAddBorrowing(@RequestBody Borrowing borrowing, @PathVariable Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.addBorrowing(borrowing);

        repository.save(user);

        EntityModel<User> entityModel = assembler.toModel(user);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/users/{id}/address")
    ResponseEntity<?> userSetAddress(@RequestBody Address address, @PathVariable Long id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setAddress(address);

        repository.save(user);

        EntityModel<User> entityModel = assembler.toModel(user);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/users/{id}/contact_information")
    ResponseEntity<?> userSetContact(@RequestBody ContactInformation contactInformation, @PathVariable Long id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setContact_information(contactInformation);

        repository.save(user);

        EntityModel<User> entityModel = assembler.toModel(user);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/users/{id}/delete")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}/borrowing_list")
    CollectionModel<EntityModel<Borrowing>> borrowings(@PathVariable Long id) {
        return service.getBorrowings(id);
    }

    @GetMapping("/users/{id}/address")
    EntityModel<Address> address(@PathVariable Long id) {
        return service.getAddress(id);
    }

    @GetMapping("/users/{id}/contact_information")
    EntityModel<ContactInformation> contact(@PathVariable Long id) {
        return service.getContact(id);
    }

    @GetMapping("/users/{id}/due_books")
    CollectionModel<EntityModel<Borrowing>> due_books(@PathVariable Long id) {

        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if(!service.isThereDueBooks(user)){
            return CollectionModel.empty();
        }

        BorrowingModelAssembler tmpAssembler = new BorrowingModelAssembler();

        List<EntityModel<Borrowing>> borrowings = (
                service.getDueBooks(user)
                .stream()
                .map(tmpAssembler::toModel)
                .collect(Collectors.toList())
        );

        return CollectionModel.of(borrowings, //
                linkTo(methodOn(UserController.class).all()).withSelfRel()

        );
    }
}
