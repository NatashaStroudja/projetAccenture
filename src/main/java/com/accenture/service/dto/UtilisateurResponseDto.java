package com.accenture.service.dto;

public record UtilisateurResponseDto(String login,
                                     String password,
                                     String nom,
                                     String prenom,
                                     String role) {
}
