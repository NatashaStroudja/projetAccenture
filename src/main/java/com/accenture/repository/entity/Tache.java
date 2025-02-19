package com.accenture.repository.entity;

import com.accenture.model.Priorite;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelle;
    private LocalDate dateLimite;
    @Enumerated(EnumType.STRING)
    private Priorite niveau;
    private Boolean termine;

}
