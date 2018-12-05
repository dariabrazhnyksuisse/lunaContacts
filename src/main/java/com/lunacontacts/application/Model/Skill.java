package com.lunacontacts.application.Model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Skill {

    @Id
    @GeneratedValue
    private Long skillId;


    @NotNull(message = "Name is required")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters long")
    private String name;

    @NotNull(message = "Level is required")
    @Size(min = 3, max = 8, message = "AWESOME, GOOD, BAD")
    private String level;

    @ManyToMany(mappedBy = "skills")
    private Set<Contact> contacts;

    public Skill() {
    }

    public Skill(String name, String level) {
        this.name = name;
        this.level = level;
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
