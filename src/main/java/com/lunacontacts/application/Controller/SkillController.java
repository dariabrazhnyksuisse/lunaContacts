package com.lunacontacts.application.Controller;


import com.lunacontacts.application.DTO.SkillDTO;
import com.lunacontacts.application.Model.Skill;
import com.lunacontacts.application.Model.SkillNotFoundException;
import com.lunacontacts.application.Repository.SkillRepository;
import com.lunacontacts.application.Service.SkillService;
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

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    //private static final Logger LOG = LoggerFactory.getLogger(SkillController.class);

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public Page<SkillDTO> retrieveAllSkills(@PageableDefault Pageable pageable) {
        return skillService.findAll(pageable);
    }

    @GetMapping("/{id}")
    private SkillDTO retrieveSkill(@PathVariable long id) throws SkillNotFoundException {
        Optional<Skill> maybeSkill = skillRepository.findById(id);

        return maybeSkill.map(skill -> modelMapper.map(skill, SkillDTO.class))
                .orElseThrow(SkillNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable long id) {
        skillRepository.deleteById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDTO createSkill(@RequestBody SkillDTO skill,
                                HttpServletRequest request, HttpServletResponse response) {
        //LOG.debug("createSkill: {}", skill.getName());
        SkillDTO createdSkill = skillService.save(skill);
        response.setHeader("Location", request
                .getRequestURL()
                .append("/")
                .append(createdSkill.getSkillId()).toString());
       // LOG.debug("Created skill {} with id {}",
                //createdSkill.getName(), createdSkill.getSkillId());
        return createdSkill;
    }

    /**
     * Update a skill.
     *
     * @param id    The id of the skill to update
     * @param skill The skill value
     * @throws SkillNotFoundException if not found.
     */
    @PutMapping(value = "/{id:\\d+}",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateSkill(
            @ApiParam(value = "The ID of the skill resource", required = true)
            @PathVariable Long id,
            @RequestBody Skill skill) {
        Optional<Skill> currentSkill = skillRepository.findById(id);
        currentSkill.ifPresentOrElse(
                newSkill -> skillService.update(newSkill, skill),
                () -> {
                    throw new SkillNotFoundException();
                });
    }

}
