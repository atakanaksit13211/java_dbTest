package io.github.atakanaksit13211.java_dbTest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ContactInformationModelAssembler implements RepresentationModelAssembler<ContactInformation, EntityModel<ContactInformation>> {

    @Override
    public EntityModel<ContactInformation> toModel(@NonNull ContactInformation contact_information) {

        return EntityModel.of(contact_information, //
                linkTo(methodOn(ContactInformationController.class).one(contact_information.getContact_information_id())).withSelfRel(),
                linkTo(methodOn(ContactInformationController.class).all()).withRel("contact_informations"),
                linkTo(methodOn(ContactInformationController.class).replaceContact_information(contact_information, contact_information.getContact_information_id())).withRel("update"),
                linkTo(methodOn(ContactInformationController.class).deleteContact_information(contact_information.getContact_information_id())).withRel("delete")
        );
    }
}