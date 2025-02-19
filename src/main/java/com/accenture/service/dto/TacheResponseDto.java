package com.accenture.service.dto;

import com.accenture.model.Priorite;

import java.time.LocalDate;

public record TacheResponseDto( int id,
                                String libelle,
                                LocalDate dateLimite,
                                Priorite niveau,
                                Boolean termine) {

}
