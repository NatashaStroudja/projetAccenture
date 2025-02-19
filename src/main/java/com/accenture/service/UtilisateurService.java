package com.accenture.service;

import com.accenture.service.dto.UtilisateurRequestDto;
import com.accenture.service.dto.UtilisateurResponseDto;

import java.util.List;

public interface UtilisateurService {

   UtilisateurResponseDto ajouter(UtilisateurRequestDto dto);
   List<UtilisateurResponseDto> liste();

}
