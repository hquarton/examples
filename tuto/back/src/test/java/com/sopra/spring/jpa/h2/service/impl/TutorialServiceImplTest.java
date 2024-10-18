package com.sopra.spring.jpa.h2.service.impl;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.dto.TutorialUpdateDTO;
import com.sopra.spring.jpa.h2.mapper.TutorialMapperImpl;
import com.sopra.spring.jpa.h2.model.Tutorial;
import com.sopra.spring.jpa.h2.repository.TutorialRepository;
import com.sopra.spring.jpa.h2.tools.TestTools;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class TutorialServiceImplTest {

    @InjectMocks
    private TutorialServiceImpl tutorialService;

    @Mock
    private TutorialRepository tutorialRepository;

    @Spy
    private TutorialMapperImpl tutorialMapper;

    private InOrder inOrder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.inOrder = Mockito.inOrder(this.tutorialRepository, this.tutorialMapper);
    }

    @Test
    void test_GetAllTutorials_whenTitleIsNull() {
        // Given
        String title = null;
        when(this.tutorialRepository.findAll()).thenReturn(new ArrayList<>(TestTools.getTutorialDTOs()));
        when(this.tutorialRepository.findByTitleContaining(anyString())).thenReturn(TestTools.getTutorialDTOsFilterTitle(title));

        // When
        List<TutorialDTO> result = new ArrayList<>(this.tutorialService.getAllTutorials(title));

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findAll();
        this.inOrder.verify(this.tutorialRepository, never()).findByTitleContaining(title);
        this.inOrder.verify(this.tutorialMapper, times(1)).tutorialsToTutorialDtos(any());

        TestTools.assertEqualsTutorials(TestTools.getTutorialDTOs(), result);
    }

    @Test
    void test_GetAllTutorials_whenTitleIsEmpty() {
        // Given
        String title = "";
        when(this.tutorialRepository.findAll()).thenReturn(new ArrayList<>(TestTools.getTutorialDTOs()));
        when(this.tutorialRepository.findByTitleContaining(anyString())).thenReturn(TestTools.getTutorialDTOsFilterTitle(title));

        // When
        List<TutorialDTO> result = new ArrayList<>(this.tutorialService.getAllTutorials(title));

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findAll();
        this.inOrder.verify(this.tutorialRepository, never()).findByTitleContaining(title);
        this.inOrder.verify(this.tutorialMapper, times(1)).tutorialsToTutorialDtos(any());

        TestTools.assertEqualsTutorials(TestTools.getTutorialDTOs(), result);
    }

    @Test
    void test_GetAllTutorials_whenTitleIsDefine() {
        // Given
        String title = "tests";
        when(this.tutorialRepository.findAll()).thenReturn(new ArrayList<>(TestTools.getTutorialDTOs()));
        when(this.tutorialRepository.findByTitleContaining(anyString())).thenReturn(TestTools.getTutorialDTOsFilterTitle(title));

        // When
        List<TutorialDTO> result = new ArrayList<>(this.tutorialService.getAllTutorials(title));

        // Then
        this.inOrder.verify(this.tutorialRepository, never()).findAll();
        this.inOrder.verify(this.tutorialRepository, times(1)).findByTitleContaining(title);
        this.inOrder.verify(this.tutorialMapper, times(1)).tutorialsToTutorialDtos(any());

        TestTools.assertEqualsTutorials(TestTools.getTutorialDTOsFilterTitle(title), result);
    }

    @Test
    void testGetTutorialById_whenTutorialIsFound() {
        // Given
        long id = 2;
        when(this.tutorialRepository.findById(anyLong())).thenReturn(TestTools.getTutorialDTOById(id));

        // When
        TutorialDTO result = this.tutorialService.getTutorialById(id);

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findById(anyLong());
        this.inOrder.verify(this.tutorialMapper, times(1)).tutorialToTutorialDto(any());

        assertNotNull(result);
        TestTools.assertEqualsTutorial(TestTools.getTutorialDTOById(id).get(), result);

    }

    @Test
    void testGetTutorialById_whenTutorialIsNotFound() {
        // Given
        long id = 10;
        when(this.tutorialRepository.findById(anyLong())).thenReturn(TestTools.getTutorialDTOById(id));

        // When
        TutorialDTO result = this.tutorialService.getTutorialById(id);

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findById(anyLong());
        this.inOrder.verify(this.tutorialMapper, never()).tutorialToTutorialDto(any());

        assertNull(result);
    }

    @Test
    void testCreateTutorial() {
        // Given
        Tutorial tutorial = TestTools.getTutorialDTOById(1L).get();
        when(this.tutorialRepository.save(any())).thenReturn(tutorial);

        // When
        TutorialDTO result = this.tutorialService.createTutorial(TestTools.getTutorialSaveDTOs());

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).save(any());
        this.inOrder.verify(this.tutorialMapper, times(1)).tutorialToTutorialDto(any());
        TestTools.assertEqualsTutorial(tutorial, result);
    }

    @Test
    void testUpdateTutorial_whenIsFound() {
        // Given
        Optional<Tutorial> tutorial = TestTools.getTutorialDTOById(1L);
        when(this.tutorialRepository.findById(anyLong())).thenReturn(tutorial);
        when(this.tutorialRepository.save(any())).thenReturn(tutorial.get());

        // When
        TutorialDTO result = this.tutorialService.updateTutorial(1, TestTools.getTutorialUpdateDto());

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findById(anyLong());
        this.inOrder.verify(this.tutorialRepository, times(1)).save(any());
        this.inOrder.verify(this.tutorialMapper, times(1)).tutorialToTutorialDto(any());
        TestTools.assertEqualsTutorial(tutorial.get(), result);
    }

    @Test
    void testUpdateTutorial_whenIsNotFound() {
        // Given
        Optional<Tutorial> tutorial = Optional.empty();
        when(this.tutorialRepository.findById(anyLong())).thenReturn(tutorial);

        // When
        TutorialDTO result = this.tutorialService.updateTutorial(1, TutorialUpdateDTO.builder().title("Task 1 - Analyse").description("Task One : analyse feature").published(true).build());

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findById(anyLong());
        this.inOrder.verify(this.tutorialRepository, never()).save(any());
        this.inOrder.verify(this.tutorialMapper, never()).tutorialToTutorialDto(any());
        assertNull(result);
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(this.tutorialRepository).deleteById(anyLong());

        // When
        this.tutorialService.deleteById(1L);

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteAll() {
        // Given
        doNothing().when(this.tutorialRepository).deleteAll();

        // When
        this.tutorialService.deleteAll();

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).deleteAll();
    }

    @Test
    void testFindByPublished_whenHasPublished() {
        // Given
        Set<Tutorial> tutorials = TestTools.getTutorialDTOsFilterIsPublished();
        when(this.tutorialRepository.findByPublished(true)).thenReturn(tutorials);

        // When
        List<TutorialDTO> tutorialDtos = new ArrayList<>(this.tutorialService.findByPublished());

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findByPublished(true);
        this.inOrder.verify(this.tutorialMapper, times(1)).tutorialsToTutorialDtos(any());
        TestTools.assertEqualsTutorials(tutorials, tutorialDtos);
    }

    @Test
    void testFindByPublished_whenHasNoPublished() {
        // Given
        Set<Tutorial> tutorials = Sets.newHashSet();
        when(this.tutorialRepository.findByPublished(true)).thenReturn(tutorials);

        // When
        List<TutorialDTO> tutorialDtos = new ArrayList<>(this.tutorialService.findByPublished());

        // Then
        this.inOrder.verify(this.tutorialRepository, times(1)).findByPublished(true);
        this.inOrder.verify(this.tutorialMapper, never()).tutorialsToTutorialDtos(any());
        TestTools.assertEqualsTutorials(tutorials, tutorialDtos);
    }
}