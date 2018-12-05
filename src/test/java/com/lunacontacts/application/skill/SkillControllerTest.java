package com.lunacontacts.application.skill;

import com.lunacontacts.application.DTO.SkillDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SkillControllerTest {

    private static final String BASE_URL = "/api/v1/skills/";
    private static final Long SKILL_ID = 1L;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mvc;

    // skills <=> JSON
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Start with one skill before each test.
     */
    @Before
    public void initTests() {
        // We start from known state, in this case 1 row in skill table.
        jdbcTemplate.execute("delete from contact_skill; delete from skill; " +
                "insert into Skill(skill_id, name, level) " +
                "values(" + SKILL_ID + ", 'Singing', 'GOOD');");
    }

    @Test
    public void shouldRetrieveAllSkills() throws Exception {
        getSkillsResponse()
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("content[0].name", is("Singing")))
                .andReturn();
    }

    @Test
    public void shouldRetrieveSkill() throws Exception {
        getSkill(SKILL_ID)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Singing")));
    }

    @Test
    public void shouldUpdateSkill() throws Exception {
        updateSkill(SKILL_ID, toJson(SkillFixture.aSkill)).andExpect(status().isNoContent());
        getSkill(SKILL_ID)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(SkillFixture.aSkill.getName())));
    }

    @Test
    public void shouldDeleteSkill() throws Exception {
        getSkill(SKILL_ID).andExpect(status().isOk());
        deleteSkill(SKILL_ID).andExpect(status().isOk());
        getSkill(SKILL_ID).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateSkill() throws Exception {
        createSkill(SkillFixture.aSkill).andExpect(status().isCreated());
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

    private ResultActions getSkillsResponse() throws Exception {
        return mvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions createSkill(SkillDTO skill) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(skill)));
    }

    private ResultActions updateSkill(Long id, byte[] skillJson) throws Exception {
        return mvc.perform(
                put(BASE_URL + id).content(skillJson).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions getSkill(Long id) throws Exception {
        return mvc.perform(get(BASE_URL + id).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions deleteSkill(Long id) throws Exception {
        return mvc.perform(delete(BASE_URL + id));
    }
}