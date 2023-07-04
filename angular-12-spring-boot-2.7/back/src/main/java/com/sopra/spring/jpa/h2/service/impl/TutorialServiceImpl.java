package com.sopra.spring.jpa.h2.service.impl;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.dto.TutorialSaveDTO;
import com.sopra.spring.jpa.h2.dto.TutorialUpdateDTO;
import com.sopra.spring.jpa.h2.mapper.TutorialMapper;
import com.sopra.spring.jpa.h2.model.Tutorial;
import com.sopra.spring.jpa.h2.repository.TutorialRepository;
import com.sopra.spring.jpa.h2.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class TutorialServiceImpl implements TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private TutorialMapper tutorialMapper;

    @Override
    public Collection<TutorialDTO> getAllTutorials(String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (StringUtils.hasText(title))
            tutorials.addAll(this.tutorialRepository.findByTitleContaining(title));
        else
            tutorials.addAll(this.tutorialRepository.findAll());

        return this.tutorialMapper.tutorialsToTutorialDtos(tutorials);
    }

    @Override
    public TutorialDTO getTutorialById(long id) {
        return this.tutorialRepository.findById(id)
                .map(tutorial -> this.tutorialMapper.tutorialToTutorialDto(tutorial))
                .orElseGet(() -> null);
    }

    @Override
    public TutorialDTO createTutorial(@Validated @NotNull TutorialSaveDTO tutorialSaveDTO) {
        return this.tutorialMapper.tutorialToTutorialDto(this.tutorialRepository
                .save(new Tutorial(tutorialSaveDTO.getTitle(), tutorialSaveDTO.getDescription(), false)));
    }

    @Override
    public TutorialDTO updateTutorial(long id, TutorialUpdateDTO tutorialUpdateDTO) {
        Optional<Tutorial> tutorialData = this.tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial tutorial = tutorialData.get();
            tutorial.setTitle(tutorialUpdateDTO.getTitle());
            tutorial.setDescription(tutorialUpdateDTO.getDescription());
            tutorial.setPublished(tutorialUpdateDTO.isPublished());
            return this.tutorialMapper.tutorialToTutorialDto(this.tutorialRepository.save(tutorial));
        }
        return null;
    }

    @Override
    public void deleteById(long id) {
        this.tutorialRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.tutorialRepository.deleteAll();
    }

    @Override
    public Collection<TutorialDTO> findByPublished() {
        Collection<Tutorial> tutorials = this.tutorialRepository.findByPublished(true);
        if (CollectionUtils.isEmpty(tutorials)) {
            return Collections.emptyList();
        }
        return this.tutorialMapper.tutorialsToTutorialDtos(tutorials);
    }
}
