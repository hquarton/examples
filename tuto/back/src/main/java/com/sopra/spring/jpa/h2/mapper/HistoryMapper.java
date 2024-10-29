package com.sopra.spring.jpa.h2.mapper;

import com.sopra.spring.jpa.h2.dto.HistoryDTO;
import com.sopra.spring.jpa.h2.model.History;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface HistoryMapper {

    HistoryDTO historyToHistoryDto(History history);

    Collection<HistoryDTO> hystoriesToHistoryDtos(Collection<History> histories);

}
