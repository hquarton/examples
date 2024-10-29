package com.sopra.spring.jpa.h2.repository;

import com.sopra.spring.jpa.h2.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
