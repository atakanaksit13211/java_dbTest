package io.github.atakanaksit13211.java_dbTest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class BorrowingModelAssembler implements RepresentationModelAssembler<Borrowing, EntityModel<Borrowing>> {

    @Override
    public EntityModel<Borrowing> toModel(@NonNull Borrowing borrowing) {

        return EntityModel.of(borrowing, //
                linkTo(methodOn(BorrowingController.class).one(borrowing.getBorrowing_id())).withSelfRel(),
                linkTo(methodOn(BorrowingController.class).all()).withRel("borrowings"),
                linkTo(methodOn(BorrowingController.class).replaceBorrowing(borrowing, borrowing.getBorrowing_id())).withRel("update"),
                linkTo(methodOn(BorrowingController.class).deleteBorrowing(borrowing.getBorrowing_id())).withRel("delete")
        );
    }

}
