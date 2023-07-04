package com.sopra.spring.jpa.h2.service;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.dto.TutorialSaveDTO;
import com.sopra.spring.jpa.h2.dto.TutorialUpdateDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface TutorialService {

    Collection<TutorialDTO> getAllTutorials(String title);

    TutorialDTO getTutorialById(long id);

    TutorialDTO createTutorial(@Validated @NotNull TutorialSaveDTO tutorialSaveDTO);

    TutorialDTO updateTutorial(long id, @Validated @NotNull TutorialUpdateDTO tutorialUpdateDTO);

    void deleteById(long id);

    void deleteAll();

    Collection<TutorialDTO> findByPublished();

}
