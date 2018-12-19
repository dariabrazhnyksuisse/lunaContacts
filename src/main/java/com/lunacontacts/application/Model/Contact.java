package com.lunacontacts.application.Model;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity
 * @Id: Specifies the primary key of an entity.
 * @GeneratedValue: Provides for the specification of generation strategies for the values of primary keys.
 */

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;

    @NotNull(message = "First name is required")
    @Size(min = 1, max = 20, message = "First name must be between 1 and 20 characters long")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 1, max = 20, message = "Last name must be between 1 and 20 characters long")
    private String lastName;

    @NotNull(message = "Full name is required")
    @Size(min = 1, max = 50, message = "Full name must be between 1 and 50 characters long")
    private String fullName;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Email is required")
    @Email(message = "Email is incorrect")
    private String email;

    @NotNull(message = "Mobile is required")
    @NumberFormat()
    private String mobile;

    /**
     * Every many-to-many association has two sides,
     * the owning side and the non-owning, or inverse, side.
     * The join table is specified on the owning side.
     * CascadeType.PERSIST : cascade type presist means that save() or persist() operations cascade to related entities.
     * CascadeType.MERGE : cascade type merge means that related entities are merged when the owning entity is merged.
     * CascadeType.REMOVE : cascade type remove removes all related entities association with this setting when the owning entity is deleted.
     */
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    @JoinTable(name = "contact_skill",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();


    public Contact() {
    }

    public Contact(String firstName, String lastName, String fullName, String address, String email, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
    }

    //getters & setters
    public Long getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
