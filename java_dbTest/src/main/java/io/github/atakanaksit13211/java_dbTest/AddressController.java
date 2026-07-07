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
class AddressController {

    private final AddressRepository repository;

    private final AddressModelAssembler assembler;

    private final AddressService service;

    AddressController(AddressRepository repository, AddressModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
        this.service = new AddressServiceImpl(repository, assembler);
    }
    // end::constructor[]

    // Aggregate root

    @GetMapping("/addresses")
    CollectionModel<EntityModel<Address>> all() {

        List<EntityModel<Address>> addresses = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(addresses, //
                linkTo(methodOn(AddressController.class).all()).withSelfRel(),
                linkTo(methodOn(AddressController.class).newAddress(new Address())).withRel("new")
        );
    }

    @PostMapping("/addresses/new")
    ResponseEntity<?> newAddress(@RequestBody Address newAddress) {

        EntityModel<Address> entityModel = assembler.toModel(repository.save(newAddress));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Single item

    @GetMapping("/addresses/{id}")
    EntityModel<Address> one(@PathVariable Long id) {

        Address address = repository.findById(id) //
                .orElseThrow(() -> new AddressNotFoundException(id));

        return assembler.toModel(address);
    }

    @PutMapping("/addresses/{id}/update")
    ResponseEntity<?> replaceAddress(@RequestBody Address newAddress, @PathVariable Long id) {

        Address updatedAddress = repository.findById(id) //
                .map(address -> {
                    address.setAddress(newAddress.getAddress());
                    address.setCity(newAddress.getCity());
                    address.setCounty(newAddress.getCounty());
                    address.setPost_code(newAddress.getPost_code());
                    return repository.save(address);
                }) //
                .orElseGet(() -> {
                    return repository.save(newAddress);
                });

        EntityModel<Address> entityModel = assembler.toModel(updatedAddress);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/addresses/{id}/delete")
    ResponseEntity<?> deleteAddress(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
