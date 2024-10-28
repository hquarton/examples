package com.sopra.spring.jpa.h2.model.listener;

import com.sopra.spring.jpa.h2.model.Tutorial;
import lombok.extern.log4j.Log4j2;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Log4j2
public class AuditTutorialListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(Tutorial tutorial) {
        if (tutorial.getId() == 0) {
            log.info("[AUDIT] About to add a tutorial");
        } else {
            log.info("[AUDIT] About to update/delete tutorial: " + tutorial.getId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(Tutorial tutorial) {
        log.info("[AUDIT] add/update/delete complete for tutorial: " + tutorial.getId());
    }

    @PostLoad
    private void afterLoad(Tutorial tutorial) {
        log.info("[AUDIT] tutorial loaded from database: " + tutorial.getId());
    }
}
