package com.lunacontacts.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunacontacts.application.Model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
