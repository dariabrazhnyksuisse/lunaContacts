package com.lunacontacts.application.contact;

import com.lunacontacts.application.DTO.ContactDTO;
import com.lunacontacts.application.Model.Contact;
import org.modelmapper.ModelMapper;

class ContactFixture {

    private static ModelMapper modelMapper = new ModelMapper();

    static final ContactDTO aContact = modelMapper.map(
            new Contact("Bob", "L'COOL", "Boob L'COOL",
                    "forest", "bob@gmail.com", "+41 78 111 11 11"), ContactDTO.class);
}
