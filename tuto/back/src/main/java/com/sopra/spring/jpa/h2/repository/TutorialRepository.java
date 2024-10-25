package com.sopra.spring.jpa.h2.repository;

import com.sopra.spring.jpa.h2.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    Collection<Tutorial> findByPublished(boolean published);

    Collection<Tutorial> findByTitleContaining(String title);
}
