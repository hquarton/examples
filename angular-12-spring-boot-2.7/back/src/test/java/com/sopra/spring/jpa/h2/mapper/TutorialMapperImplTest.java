package com.sopra.spring.jpa.h2.mapper;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.model.Tutorial;
import com.sopra.spring.jpa.h2.tools.TestTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

class TutorialMapperImplTest {

    @Spy
    private TutorialMapperImpl tutorialMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTutorialToTutorialDto() {
        // Given
        Tutorial tutorial = TestTools.getTutorialDTOById(1L).get();
        TutorialDTO expected = TutorialDTO.builder().id(1L).title("Task 1 - Analyse").description("Task One : analyse feature").published(true).build();

        // When
        TutorialDTO result = this.tutorialMapper.tutorialToTutorialDto(tutorial);

        // Then
        TestTools.assertEqualsTutorialDto(expected, result);
    }

    @Test
    void testTutorialToTutorialDto_whenIsNull() {
        // Given
        Tutorial tutorial = null;

        // When
        TutorialDTO result = this.tutorialMapper.tutorialToTutorialDto(tutorial);

        // Then
        assertNull(result);
    }

    @Test
    void testTutorialsToTutorialDtos() {
        // Given
        Collection<Tutorial> tutorials = List.of(TestTools.getTutorialDTOById(1L).get());
        Collection<TutorialDTO> expected = List.of(TutorialDTO.builder().id(1L).title("Task 1 - Analyse").description("Task One : analyse feature").published(true).build());

        // When
        Collection<TutorialDTO> result = this.tutorialMapper.tutorialsToTutorialDtos(tutorials);

        // Then
        TestTools.assertEqualsTutorialDtos(expected, result);
    }

    @Test
    void testTutorialsToTutorialDtos_whenIsEmpty() {
        // Given
        Collection<Tutorial> tutorials = Collections.emptyList();
        Collection<TutorialDTO> expected = Collections.emptyList();

        // When
        Collection<TutorialDTO> result = this.tutorialMapper.tutorialsToTutorialDtos(tutorials);

        // Then
        TestTools.assertEqualsTutorialDtos(expected, result);
    }

    @Test
    void testTutorialsToTutorialDtos_whenIsNull() {
        // Given
        Collection<Tutorial> tutorials = null;

        // When
        Collection<TutorialDTO> result = this.tutorialMapper.tutorialsToTutorialDtos(tutorials);

        // Then
        assertNull(result);
    }
}