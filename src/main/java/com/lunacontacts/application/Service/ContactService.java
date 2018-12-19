package com.lunacontacts.application.Service;

import com.lunacontacts.application.DTO.ContactDTO;
import com.lunacontacts.application.Model.Contact;
import com.lunacontacts.application.Repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service layer
 * Pageable = abstract interface for pagination information.
 */
@Service
public class ContactService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactService.class);
    private final ContactRepository contactRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    public void update(Contact oldContact, Contact newContact) {
        LOG.debug("update contact: modified from {} to {}", oldContact, newContact);
        newContact.setContactId(oldContact.getContactId());
        this.contactRepository.save(newContact);
    }

    public ContactDTO save(ContactDTO newContactDTO) {
        LOG.debug("creating contact: {}", newContactDTO);
        // map (instance, destination)
        Contact contact = this.contactRepository.save(modelMapper.map(newContactDTO, Contact.class));
        newContactDTO.setId(contact.getContactId());
        return newContactDTO;
    }

    public Page<ContactDTO> findAll(Pageable pageable) {
        return contactRepository.findAll(pageable).map(contact -> modelMapper.map(contact, ContactDTO.class));
    }
}
