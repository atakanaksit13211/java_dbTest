package io.github.atakanaksit13211.java_dbTest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(@NonNull Book book) {

        return EntityModel.of(book, //
                linkTo(methodOn(BookController.class).one(book.getBook_id())).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("books"),
                linkTo(methodOn(BookController.class).replaceBook(book, book.getBook_id())).withRel("update"),
                linkTo(methodOn(BookController.class).deleteBook(book.getBook_id())).withRel("delete")
        );
    }
}
