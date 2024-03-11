package com.sopra.spring.jpa.h2.controller;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.dto.TutorialSaveDTO;
import com.sopra.spring.jpa.h2.dto.TutorialUpdateDTO;
import com.sopra.spring.jpa.h2.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @GetMapping("/tutorials")
    public ResponseEntity<Collection<TutorialDTO>> getAllTutorials(@RequestParam(required = false) String title) {
        List<TutorialDTO> tutorials = new ArrayList<>(this.tutorialService.getAllTutorials(title));

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDTO> getTutorialById(@PathVariable("id") long id) {
        TutorialDTO tutorialDataDto = this.tutorialService.getTutorialById(id);
        if (tutorialDataDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tutorialDataDto, HttpStatus.OK);
    }

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialDTO> createTutorial(@Valid @RequestBody TutorialSaveDTO tutorialSaveDTO) {
        TutorialDTO tutorial = this.tutorialService.createTutorial(tutorialSaveDTO);
        return new ResponseEntity<>(tutorial, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDTO> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialUpdateDTO tutorialUpdateDTO) {
        TutorialDTO tutorialDTO = this.tutorialService.updateTutorial(id, tutorialUpdateDTO);
        if (tutorialDTO != null) {
            return new ResponseEntity<>(tutorialDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        this.tutorialService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        this.tutorialService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<Collection<TutorialDTO>> findByPublished() {
        List<TutorialDTO> tutorials = new ArrayList<>(this.tutorialService.findByPublished());

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}
