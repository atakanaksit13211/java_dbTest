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
class BorrowingController {

    private final BorrowingRepository repository;

    private final BorrowingModelAssembler assembler;

    BorrowingController(BorrowingRepository repository, BorrowingModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }
    // end::constructor[]

    // Aggregate root

    @GetMapping("/borrowings")
    CollectionModel<EntityModel<Borrowing>> all() {

        List<EntityModel<Borrowing>> borrowings = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(borrowings, //
                linkTo(methodOn(BorrowingController.class).all()).withSelfRel(),
                linkTo(methodOn(BorrowingController.class).newBorrowing(new Borrowing())).withRel("new")
        );
    }

    @PostMapping("/borrowings/new")
    ResponseEntity<?> newBorrowing(@RequestBody Borrowing newBorrowing) {

        EntityModel<Borrowing> entityModel = assembler.toModel(repository.save(newBorrowing));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Single item

    @GetMapping("/borrowings/{id}")
    EntityModel<Borrowing> one(@PathVariable Long id) {

        Borrowing borrowing = repository.findById(id) //
                .orElseThrow(() -> new BorrowingNotFoundException(id));

        return assembler.toModel(borrowing);
    }

    @PutMapping("/borrowings/{id}/update")
    ResponseEntity<?> replaceBorrowing(@RequestBody Borrowing newBorrowing, @PathVariable Long id) {

        Borrowing updatedBorrowing = repository.findById(id) //
                .map(borrowing -> {
                    borrowing.setBorrowed_timestamp(newBorrowing.getBorrowed_timestamp());
                    borrowing.setDue_timestamp(newBorrowing.getDue_timestamp());
                    borrowing.setReturned(newBorrowing.isReturned());
                    borrowing.setBook(newBorrowing.getBook());
                    borrowing.setUser(newBorrowing.getUser());
                    return repository.save(borrowing);
                }) //
                .orElseGet(() -> {
                    return repository.save(newBorrowing);
                });

        EntityModel<Borrowing> entityModel = assembler.toModel(updatedBorrowing);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/borrowings/{id}/delete")
    ResponseEntity<?> deleteBorrowing(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
