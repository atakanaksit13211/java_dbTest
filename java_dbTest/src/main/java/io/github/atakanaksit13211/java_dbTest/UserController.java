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

    UserController(UserRepository repository, UserModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }
    // end::constructor[]

    // Aggregate root

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all() {

        List<EntityModel<User>> users = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/users")
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

    @PutMapping("/users/{id}")
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

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
