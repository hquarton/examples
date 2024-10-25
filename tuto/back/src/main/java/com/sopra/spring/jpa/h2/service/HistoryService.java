package com.sopra.spring.jpa.h2.service;

import com.sopra.spring.jpa.h2.model.Tutorial;

public interface HistoryService {

    /**
     * Sauvegarde d'une modification d'un tutorial
     *
     * @param tutorial    Tutorial
     * @param attribut    le nom du champ modifié
     * @param valeurAvant la valeur du champ avant la modification
     * @param valeurApres la valeur du champ après la modification
     * @return l'historisation créée
     */
    void create(Tutorial tutorial, String attribut, String valeurAvant, String valeurApres);
}
