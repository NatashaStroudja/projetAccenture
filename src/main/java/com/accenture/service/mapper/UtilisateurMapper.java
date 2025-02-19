package com.accenture.service.mapper;

import com.accenture.repository.entity.Utilisateur;
import com.accenture.service.dto.UtilisateurRequestDto;
import com.accenture.service.dto.UtilisateurResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    Utilisateur toUtilisateur (UtilisateurRequestDto utilisateurRequestDto);
    UtilisateurResponseDto toUtilisateurResponseDto (Utilisateur utilisateur);
}
