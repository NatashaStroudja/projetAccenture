package com.accenture.controller;

import com.accenture.model.Priorite;
import com.accenture.repository.entity.Tache;
import com.accenture.service.TacheService;
import com.accenture.service.dto.TacheRequestDto;
import com.accenture.service.dto.TacheResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/taches")
public class TacheController {

    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }
    @GetMapping
    List<TacheResponseDto> taches(){
        return tacheService.trouverToutes();
    }

    @GetMapping("/search")
    List<TacheResponseDto> recherche(
           @RequestParam(required = false) String libelle,
           @RequestParam(required = false ) LocalDate dateLimite,
           @RequestParam(required = false)Priorite  priorite,
           @RequestParam (required = false)Boolean  termine

    ){
        return tacheService.rechercher(libelle,dateLimite,priorite,termine);
    }

    @GetMapping("/{id}")
    ResponseEntity<TacheResponseDto> uneTache(@PathVariable("id") int id){
        TacheResponseDto trouve = tacheService.trouver(id);
        return ResponseEntity.ok(trouve);
    }
    @PostMapping//si on envoie rien , on mets void, sinon on fournie tache
    ResponseEntity<Void> ajouter(@RequestBody @Valid TacheRequestDto tacheRequestDto){
        TacheResponseDto tacheEnreg = tacheService.ajouter(tacheRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tacheEnreg.id())//va remplasser "{if}" par sa valeur
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping ("/{id}")
    ResponseEntity<Void> suppr(@PathVariable("id") int id){
        tacheService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
@PutMapping("/{id}")
    ResponseEntity<TacheResponseDto> modifier(@PathVariable("id")  int id, @RequestBody @Valid TacheRequestDto tacheRequestDto){
        TacheResponseDto reponse = tacheService.modifier(id, tacheRequestDto);
        return ResponseEntity.ok(reponse);
}
@PatchMapping("/{id}")
    ResponseEntity<TacheResponseDto> modifierPart(@PathVariable("id") int id, @RequestBody TacheRequestDto tacheRequestDto){
        TacheResponseDto reponse = tacheService.modifierPartiellement(id, tacheRequestDto);
        return ResponseEntity.ok(reponse);
}

}
