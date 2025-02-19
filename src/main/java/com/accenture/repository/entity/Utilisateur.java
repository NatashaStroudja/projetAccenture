package com.accenture.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Utilisateur {
    @Id
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String role;
}
