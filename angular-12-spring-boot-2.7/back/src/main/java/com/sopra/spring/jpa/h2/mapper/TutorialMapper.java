package com.sopra.spring.jpa.h2.mapper;

import com.sopra.spring.jpa.h2.dto.TutorialDTO;
import com.sopra.spring.jpa.h2.model.Tutorial;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TutorialMapper {

    TutorialDTO tutorialToTutorialDto(Tutorial tutorialDTO);

    Collection<TutorialDTO> tutorialsToTutorialDtos(Collection<Tutorial> tutorialDTO);

}
