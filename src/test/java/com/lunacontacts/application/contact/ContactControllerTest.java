package com.lunacontacts.application.contact;

import com.lunacontacts.application.DTO.ContactDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * JdbcTemplate
 * @Autowired
 * MockMvc
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContactControllerTest {

    private static final String BASE_URL = "/api/v1/contacts/";
    private static final Long CONTACT_ID = 10L;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mvc;

    // contacts <=> JSON
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Start with one contact before each test.
     */
    @Before
    public void initTests() {
        // We start from known state, in this case 1 row in contact table.
        jdbcTemplate.execute("delete from contact_skill; delete from contact; " +
                "insert into Contact(contact_id, first_name, last_name, full_name, address, email, mobile) " +
                "values(" + CONTACT_ID + ", 'Tom', 'WHITE', 'Tom WHITE', 'Sion 1950', 'tom@gmail.com', '+41 78 555 55 55');");
    }

    @Test
    public void shouldRetrieveAllContacts() throws Exception {
        getContactsResponse()
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("content[0].firstName", is("Tom")))
                .andReturn();
    }

    @Test
    public void shouldRetrieveContact() throws Exception {
        getContact(CONTACT_ID)
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("Tom")));
    }

    @Test
    public void shouldUpdateContact() throws Exception {
        updateContact(CONTACT_ID, toJson(ContactFixture.aContact)).andExpect(status().isNoContent());
        getContact(CONTACT_ID)
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is(ContactFixture.aContact.getFirstName())));
    }

    @Test
    public void shouldDeleteContact() throws Exception {
        getContact(CONTACT_ID).andExpect(status().isOk());
        deleteContact(CONTACT_ID).andExpect(status().isOk());
        getContact(CONTACT_ID).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateContact() throws Exception {
        createContact(ContactFixture.aContact).andExpect(status().isCreated());
    }

    /**
     * Convert object to JSON bytes.
     *
     * @param object
     *            The object to JSONify
     * @return byte array with JSON representation
     * @throws Exception
     */
    private byte[] toJson(Object object) throws Exception {
        return this.mapper.writeValueAsString(object).getBytes();
    }

    private ResultActions getContactsResponse() throws Exception {
        return mvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions createContact(ContactDTO contact) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(contact)));
    }

    private ResultActions updateContact(Long id, byte[] contactJson) throws Exception {
        return mvc.perform(
                put(BASE_URL + id).content(contactJson).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions getContact(Long id) throws Exception {
        return mvc.perform(get(BASE_URL + id).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions deleteContact(Long id) throws Exception {
        return mvc.perform(delete(BASE_URL + id));
    }
}