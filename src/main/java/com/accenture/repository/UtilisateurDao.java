package com.accenture.repository;

import com.accenture.repository.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurDao extends JpaRepository<Utilisateur, String> {
}
