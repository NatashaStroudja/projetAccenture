package com.accenture.controller;

import com.accenture.service.UtilisateurService;
import com.accenture.service.dto.UtilisateurRequestDto;
import com.accenture.service.dto.UtilisateurResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody UtilisateurRequestDto requestDto){
        utilisateurService.ajouter(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
@GetMapping
    List<UtilisateurResponseDto> tous(){
        return utilisateurService.liste();
}

}
