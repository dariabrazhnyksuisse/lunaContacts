package com.lunacontacts.application.DTO;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lunacontacts.application.Model.Contact;

/**
 * Data Transfer Objects - Skill
 */
public class SkillDTO {

    private Long skillId;
    private String name;
    private String level;

    @JsonIgnore
    private Set<Contact> contacts;

    public SkillDTO() {
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
