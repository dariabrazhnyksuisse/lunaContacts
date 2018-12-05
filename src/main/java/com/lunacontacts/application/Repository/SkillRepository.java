package com.lunacontacts.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunacontacts.application.Model.Skill;

/**
 * Persistence layer
 *
 * We define a simple repository and use it to insert, update, delete and retrieve
 * 'todo' entities from the database - without writing a lot of code.
 * Interface JpaRepository<T,ID>
 */
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
