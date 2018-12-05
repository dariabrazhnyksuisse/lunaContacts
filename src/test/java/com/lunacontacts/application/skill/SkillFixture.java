package com.lunacontacts.application.skill;
import com.lunacontacts.application.DTO.SkillDTO;
import com.lunacontacts.application.Model.Skill;
import org.modelmapper.ModelMapper;

class SkillFixture {

    private static ModelMapper modelMapper = new ModelMapper();

    static final SkillDTO aSkill = modelMapper.map(
            new Skill("Painting", "GOOD"), SkillDTO.class);
}
