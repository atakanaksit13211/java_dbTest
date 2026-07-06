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
class ContactInformationController {

    private final ContactInformationRepository repository;

    private final ContactInformationModelAssembler assembler;

    ContactInformationController(ContactInformationRepository repository, ContactInformationModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }
    // end::constructor[]

    // Aggregate root

    @GetMapping("/contact_informations")
    CollectionModel<EntityModel<ContactInformation>> all() {

        List<EntityModel<ContactInformation>> contactInformations = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(contactInformations, //
                linkTo(methodOn(ContactInformationController.class).all()).withSelfRel(),
                linkTo(methodOn(ContactInformationController.class).newContact_information(new ContactInformation())).withRel("new")
        );
    }

    @PostMapping("/contact_informations/new")
    ResponseEntity<?> newContact_information(@RequestBody ContactInformation newContactInformation) {

        EntityModel<ContactInformation> entityModel = assembler.toModel(repository.save(newContactInformation));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Single item

    @GetMapping("/contact_informations/{id}")
    EntityModel<ContactInformation> one(@PathVariable Long id) {

        ContactInformation contactInformation = repository.findById(id) //
                .orElseThrow(() -> new ContactInformationNotFoundException(id));

        return assembler.toModel(contactInformation);
    }

    @PutMapping("/contact_informations/{id}/update")
    ResponseEntity<?> replaceContact_information(@RequestBody ContactInformation newContact_information, @PathVariable Long id) {

        ContactInformation updatedContactInformation = repository.findById(id) //
                .map(contact_information -> {
                    contact_information.setPrimary_phone_number(newContact_information.getPrimary_phone_number());
                    contact_information.setAlternative_email(newContact_information.getAlternative_email());
                    return repository.save(contact_information);
                }) //
                .orElseGet(() -> {
                    return repository.save(newContact_information);
                });

        EntityModel<ContactInformation> entityModel = assembler.toModel(updatedContactInformation);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/contact_informations/{id}/delete")
    ResponseEntity<?> deleteContact_information(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
