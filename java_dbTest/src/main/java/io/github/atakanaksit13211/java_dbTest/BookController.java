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
class BookController {

    private final BookRepository repository;

    private final BookModelAssembler assembler;

    private final BookService service;

    BookController(BookRepository repository, BookModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
        this.service = new BookServiceImpl(repository, assembler);
    }
    // end::constructor[]

    // Aggregate root

    @GetMapping("/books")
    CollectionModel<EntityModel<Book>> all() {

        List<EntityModel<Book>> books = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(books, //
                linkTo(methodOn(BookController.class).all()).withSelfRel(),
                linkTo(methodOn(BookController.class).newBook(new Book())).withRel("new")
        );
    }

    @PostMapping("/books/new")
    ResponseEntity<?> newBook(@RequestBody Book newBook) {

        EntityModel<Book> entityModel = assembler.toModel(repository.save(newBook));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Single item

    @GetMapping("/books/{id}")
    EntityModel<Book> one(@PathVariable Long id) {

        Book book = repository.findById(id) //
                .orElseThrow(() -> new BookNotFoundException(id));

        return assembler.toModel(book);
    }

    @PutMapping("/books/{id}/update")
    ResponseEntity<?> replaceBook(@RequestBody Book newBook, @PathVariable Long id) {

        Book updatedBook = repository.findById(id) //
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    book.setIsbn(newBook.getIsbn());
                    book.setPublisher(newBook.getPublisher());
                    return repository.save(book);
                }) //
                .orElseGet(() -> {
                    return repository.save(newBook);
                });

        EntityModel<Book> entityModel = assembler.toModel(updatedBook);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/books/{id}/delete")
    ResponseEntity<?> deleteBook(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
