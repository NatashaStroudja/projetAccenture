package com.accenture.service;

import com.accenture.exception.TacheException;
import com.accenture.model.Priorite;
import com.accenture.repository.entity.Tache;
import com.accenture.service.dto.TacheRequestDto;
import com.accenture.service.dto.TacheResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface TacheService {
    TacheResponseDto trouver(int id) throws EntityNotFoundException;
    List<TacheResponseDto> trouverToutes();
    TacheResponseDto ajouter(TacheRequestDto tacheRequestDto) throws TacheException;//param de la tache que user a creé
    TacheResponseDto modifier (int id, TacheRequestDto tacheRequestDto) throws TacheException;// on a besoin de id et de nouvelle tache
    TacheResponseDto modifierPartiellement(int id, TacheRequestDto tacheRequestDto) throws TacheException;
    void supprimer (int id)throws EntityNotFoundException;

    List<TacheResponseDto> rechercher(String libelle, LocalDate dateLimite, Priorite priorite, Boolean termine);
}
