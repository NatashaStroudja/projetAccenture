package com.accenture.service.dto;

import com.accenture.model.Priorite;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TacheRequestDto(
        @NotBlank(message = "Le libelle est obligatoire")
        String libelle,
        @NotNull(message = "La date limite est obligatoire")
        @FutureOrPresent(message = "La date limite doit etre posterieure ou egale à aujourdhui")
        LocalDate dateLimite,
        @NotNull(message = "La priorite est obligatoire")
        Priorite niveau,
        @NotNull(message = "Le termine est obligatoire")
        Boolean termine) {

}
