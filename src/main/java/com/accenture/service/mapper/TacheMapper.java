package com.accenture.service.mapper;

import com.accenture.repository.entity.Tache;
import com.accenture.service.dto.TacheRequestDto;
import com.accenture.service.dto.TacheResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TacheMapper {
    Tache toTache(TacheRequestDto tacheRequestDto);
    TacheResponseDto toTacheResponseDto(Tache tache);
}
