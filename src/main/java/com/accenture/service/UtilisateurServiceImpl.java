package com.accenture.service;

import com.accenture.repository.UtilisateurDao;
import com.accenture.repository.entity.Utilisateur;
import com.accenture.service.dto.UtilisateurRequestDto;
import com.accenture.service.dto.UtilisateurResponseDto;
import com.accenture.service.mapper.UtilisateurMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDao utilisateurDao;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;



    public UtilisateurServiceImpl(UtilisateurDao utilisateurDao, UtilisateurMapper utilisateurMapper, PasswordEncoder passwordEncoder) {
        this.utilisateurDao = utilisateurDao;
        this.utilisateurMapper = utilisateurMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UtilisateurResponseDto ajouter(UtilisateurRequestDto dto) {
        Utilisateur utilisateur = utilisateurMapper.toUtilisateur(dto);
        utilisateur.setRole("ROLE_ADMIN");
        String passwordChiffre = passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(passwordChiffre);
        Utilisateur utilisateurEnreg = utilisateurDao.save(utilisateur);

        return utilisateurMapper.toUtilisateurResponseDto(utilisateurEnreg);
    }

    @Override
    public List<UtilisateurResponseDto> liste() {
        List<Utilisateur> listeU = utilisateurDao.findAll();
        return listeU.stream()
                .map(utilisateurMapper::toUtilisateurResponseDto)
                .toList();
    }
}
