package io.github.atakanaksit13211.java_dbTest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {

        return EntityModel.of(user, //
                linkTo(methodOn(UserController.class).one(user.getUser_id())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}
