package io.github.atakanaksit13211.java_dbTest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class AddressModelAssembler implements RepresentationModelAssembler<Address, EntityModel<Address>> {

    @Override
    public EntityModel<Address> toModel(@NonNull Address address) {

        return EntityModel.of(address, //
                linkTo(methodOn(AddressController.class).one(address.getAddress_id())).withSelfRel(),
                linkTo(methodOn(AddressController.class).all()).withRel("addresses"),
                linkTo(methodOn(AddressController.class).replaceAddress(address, address.getAddress_id())).withRel("update"),
                linkTo(methodOn(AddressController.class).deleteAddress(address.getAddress_id())).withRel("delete")
        );
    }
}