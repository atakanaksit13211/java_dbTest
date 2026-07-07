package io.github.atakanaksit13211.java_dbTest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(@NonNull User user) {

        return EntityModel.of(user, //
                linkTo(methodOn(UserController.class).one(user.getUser_id())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"),
                linkTo(methodOn(UserController.class).replaceUser(user, user.getUser_id())).withRel("update"),
                linkTo(methodOn(UserController.class).deleteUser(user.getUser_id())).withRel("delete"),
                linkTo(methodOn(UserController.class).borrowings(user.getUser_id())).withRel("borrowings"),
                linkTo(methodOn(UserController.class).due_books(user.getUser_id())).withRel("due_books")

        );
    }
}
