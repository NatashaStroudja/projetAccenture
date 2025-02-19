package com.accenture.service.dto;

public record UtilisateurRequestDto(String login,
                                    String password,
                                    String nom,
                                    String prenom
                                    ) {
}
