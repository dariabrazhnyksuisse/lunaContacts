package com.lunacontacts.application.Controller;

import com.lunacontacts.application.DTO.ContactDTO;
import com.lunacontacts.application.Model.Contact;
import com.lunacontacts.application.Model.ContactNotFoundException;
import com.lunacontacts.application.Repository.ContactRepository;
import com.lunacontacts.application.Service.ContactService;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


/**
 * CRUD operations
 * ModelMapper -> to make object mapping easy, by automatically determining how one object model maps to another
 * @use ContactRepository, ContactService
 */
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    //private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);
    private final ContactRepository contactRepository;
    private final ContactService contactService;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactController(ContactRepository contactRepository, ContactService contactService, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public Page<ContactDTO> retrieveAllContacts(@PageableDefault Pageable pageable) {
        return contactService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ContactDTO retrieveContact(@PathVariable long id) throws ContactNotFoundException {
        Optional<Contact> maybeContact = contactRepository.findById(id);
        return maybeContact.map(contact -> modelMapper.map(contact, ContactDTO.class))
                .orElseThrow(ContactNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable long id) {
        contactRepository.deleteById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@RequestBody ContactDTO contact,
                                    HttpServletRequest request, HttpServletResponse response) {
        //LOG.debug("createContact: {}", contact.getFirstName());
        ContactDTO createdContact = contactService.save(contact);
        response.setHeader("Location", request
                .getRequestURL()
                .append("/")
                .append(createdContact.getId()).toString());
        /*LOG.debug("Created contact {} with id {}",
                //createdContact.getFirstName(), createdContact.getId());*/
        return createdContact;
    }

    /**
     * Update a contact.
     *
     * @param id      The id of the contact to update
     * @param contact The contact with updated fields
     * @throws ContactNotFoundException if not found.
     */
    @PutMapping(value = "/{id:\\d+}",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateContact(
            @ApiParam(value = "The ID of the contact resource", required = true)
            @PathVariable Long id,
            @RequestBody Contact contact) {
        Optional<Contact> currentContact = contactRepository.findById(id);
        currentContact.ifPresentOrElse(
                newContact -> contactService.update(newContact, contact),
                () -> {
                    throw new ContactNotFoundException();
                });
    }

}
