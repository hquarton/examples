package com.sopra.spring.jpa.h2.service;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.dto.TutorialSaveDTO;
import com.sopra.spring.jpa.h2.dto.TutorialUpdateDTO;

import java.util.Collection;

public interface TutorialService {

    Collection<TutorialDTO> getAllTutorials(String title);

    TutorialDTO getTutorialById(long id);

    TutorialDTO createTutorial(TutorialSaveDTO tutorialSaveDTO);

    TutorialDTO updateTutorial(long id, TutorialUpdateDTO tutorialUpdateDTO);

    void deleteById(long id);

    void deleteAll();

    Collection<TutorialDTO> findByPublished();

}
