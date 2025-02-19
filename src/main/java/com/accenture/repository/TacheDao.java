package com.accenture.repository;

import com.accenture.model.Priorite;
import com.accenture.repository.entity.Tache;
import com.accenture.service.dto.TacheResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TacheDao extends JpaRepository <Tache,Integer> {
    List<Tache> findByLibelleContaining(String libelle);

    List<Tache> findByDateLimite(LocalDate dateLimite);

    List<Tache> findByNiveau(Priorite priorite);

    List<Tache> findByTermine(Boolean termine);
}
