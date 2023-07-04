package com.sopra.spring.jpa.h2.tools;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.dto.TutorialSaveDTO;
import com.sopra.spring.jpa.h2.dto.TutorialUpdateDTO;
import com.sopra.spring.jpa.h2.model.Tutorial;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public final class TestTools {

    public static Set<Tutorial> getTutorialDTOsFilterTitle(String title) {
        return TestTools.getTutorialDTOs().stream().filter(tutorial -> title != null && tutorial.getTitle().contains(title)).collect(Collectors.toSet());
    }

    public static Set<Tutorial> getTutorialDTOsFilterIsPublished() {
        return TestTools.getTutorialDTOs().stream().filter(Tutorial::isPublished).collect(Collectors.toSet());
    }


    public static Set<Tutorial> getTutorialDTOs() {
        return Set.of(
                Tutorial.builder().id(1L).title("Task 1 - Analyse").description("Task One : analyse feature").published(true).build(),
                Tutorial.builder().id(2L).title("Task 2 - model").description("Task Two : modeling feature and architecture").published(false).build(),
                Tutorial.builder().id(3L).title("Task 3 - dataset").description("Task Three : dataset for developers and functional tests").published(false).build(),
                Tutorial.builder().id(4L).title("Task 4 - build & tests").description("Task For : develop, build, unit tests, integration tests").published(false).build(),
                Tutorial.builder().id(5L).title("Task 5 - deploy & tests").description("Task Five : Deploy & functional tests").published(false).build()
        );
    }

    public static void assertEqualsTutorials(Set<Tutorial> tutorialDTOs, List<TutorialDTO> result) {
        assertEquals(tutorialDTOs.size(), result.size());
        tutorialDTOs.forEach(expected -> {
            Optional<TutorialDTO> first = result.stream().filter(tutorialDTO -> tutorialDTO.getTitle().equals(expected.getTitle())).findFirst();
            if (first.isPresent()) {
                TestTools.assertEqualsTutorial(expected, first.get());
            } else {
                fail();
            }
        });
    }

    public static void assertEqualsTutorialDtos(Collection<TutorialDTO> tutorialDTOs, Collection<TutorialDTO> result) {
        assertEquals(tutorialDTOs.size(), result.size());
        tutorialDTOs.forEach(expected -> {
            Optional<TutorialDTO> first = result.stream().filter(tutorialDTO -> tutorialDTO.getTitle().equals(expected.getTitle())).findFirst();
            if (first.isPresent()) {
                TestTools.assertEqualsTutorialDto(expected, first.get());
            } else {
                fail();
            }
        });
    }

    public static void assertEqualsTutorial(Tutorial expected, TutorialDTO result) {
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.isPublished(), result.isPublished());
    }

    public static void assertEqualsTutorialDto(TutorialDTO expected, TutorialDTO result) {
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.isPublished(), result.isPublished());
        assertEquals(expected, result);
    }

    public static Optional<Tutorial> getTutorialDTOById(long id) {
        return TestTools.getTutorialDTOs().stream().filter(tutorial -> tutorial.getId() == id).findFirst();
    }

    public static TutorialSaveDTO getTutorialSaveDTOs() {
        return TutorialSaveDTO.builder().title("Task 1 - Analyse").description("Task One : analyse feature").build();
    }

    public static TutorialUpdateDTO getTutorialUpdateDto() {
        return TutorialUpdateDTO.builder().title("Task 1 - Analyse").description("Task One : analyse feature").published(true).build();
    }
}
