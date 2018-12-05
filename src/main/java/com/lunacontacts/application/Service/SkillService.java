package com.lunacontacts.application.Service;

import com.lunacontacts.application.DTO.SkillDTO;
import com.lunacontacts.application.Model.Skill;
import com.lunacontacts.application.Repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service layer
 */

@Service
public class SkillService {

    private final ModelMapper modelMapper;
    private static final Logger LOG = LoggerFactory.getLogger(SkillService.class);
    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(ModelMapper modelMapper, SkillRepository skillRepository) {
        this.modelMapper = modelMapper;
        this.skillRepository = skillRepository;
    }

    public void update(Skill oldSkill, Skill newSkill) {
        LOG.debug("update skill: modified from {} to {}", oldSkill, newSkill);
        newSkill.setSkillId(oldSkill.getSkillId());
        this.skillRepository.save(newSkill);
    }

    public SkillDTO save(SkillDTO newSkillDTO) {
        LOG.debug("creating skill: {}", newSkillDTO);
        Skill skill = this.skillRepository.save(modelMapper.map(newSkillDTO, Skill.class));
        newSkillDTO.setSkillId(skill.getSkillId());
        return newSkillDTO;
    }

    public Page<SkillDTO> findAll(Pageable pageable) {
        return skillRepository.findAll(pageable).map(skill -> modelMapper.map(skill, SkillDTO.class));
    }
}