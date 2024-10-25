package com.sopra.spring.jpa.h2.service.impl;

import com.sopra.spring.jpa.h2.model.History;
import com.sopra.spring.jpa.h2.model.Tutorial;
import com.sopra.spring.jpa.h2.repository.HistoryRepository;
import com.sopra.spring.jpa.h2.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repository;

    @Override
    public void create(Tutorial tutorial, String attribut, String valeurAvant, String valeurApres) {
        History history = History.builder()
                .tutorial(tutorial)
                .field(attribut)
                .before(valeurAvant)
                .after(valeurApres)
                .build();
        this.repository.save(history);
    }
}
