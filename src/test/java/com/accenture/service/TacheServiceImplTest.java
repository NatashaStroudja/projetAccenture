package com.accenture.service;

import com.accenture.exception.TacheException;
import com.accenture.model.Priorite;
import com.accenture.repository.TacheDao;
import com.accenture.repository.entity.Tache;
import com.accenture.service.dto.TacheRequestDto;
import com.accenture.service.dto.TacheResponseDto;
import com.accenture.service.mapper.TacheMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)//pour dire a Junit5 qu'on va utiliser @Mock
class TacheServiceImplTest {
    @Mock
    TacheDao daoMock;
    @Mock
    TacheMapper mapperMock;
    @InjectMocks
    TacheServiceImpl service;

    @BeforeEach
    void init() {
       /* daoMock = Mockito.mock(TacheDao.class);
        mapperMock = Mockito.mock(TacheMapper.class);
        service = Mockito.mock(daoMock, mapperMock);*/
    }


    @DisplayName("""
            Test de la methode trouver(int id) qui doit renvoyer une exception lors que la tache n'existe pas
            """)
    @Test
    void testTrouverExistePas() {
        //simulation que la tache n'existe pas en base
        Mockito.when(daoMock.findById(50)).thenReturn(Optional.empty());
        //verifier que la methode service.trouver(50) renvoie une exception
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(50));
        assertEquals("L'id non present", ex.getMessage());
    }

    @DisplayName("""
            Test de la methode trouver(int id) qui doit renvoyer un TacheResponseDto lors que la tache existe 
            """)
    @Test
    void testTrouverExiste() {
        //simulation que la tache existe en base
        Tache t = creeTacheCadeaux();
        Optional<Tache> optTache = Optional.of(t);
        Mockito.when(daoMock.findById(1)).thenReturn(optTache);

        TacheResponseDto dto = creeTacheResponseDtoCadeaux();
        Mockito.when(mapperMock.toTacheResponseDto(t)).thenReturn(dto);

        assertSame(dto, service.trouver(1));

    }

    private static Tache creeTacheCadeaux() {
        Tache t = new Tache();
        t.setId(1);
        t.setDateLimite(LocalDate.of(2024, 12, 24));
        t.setLibelle("achter les cadeaux");
        t.setNiveau(Priorite.BAS);
        t.setTermine(false);
        return t;


    }

    private static TacheResponseDto creeTacheResponseDtoCadeaux() {
      return   new TacheResponseDto(1, "acheter les cadeaux", LocalDate.of(2024, 12, 24), Priorite.BAS, false);
    }

    @DisplayName("""
            Test de la methode trouverToutes() qui doit renvoyer une liste correspondante a la liste dans la base
            """)
    @Test
    void testTrouverToutes() {
        Tache tacheCadeaux = creeTacheCadeaux();
        Tache tachePromenade = creeTachePromenade();

        List<Tache> taches = List.of(creeTachePromenade(), creeTacheCadeaux());
        TacheResponseDto tacheResponseDtoCadeaux = creeTacheResponseDtoCadeaux();
        TacheResponseDto tacheResponseDtoPromenade = creeTacheResponseDtoPromener();
        List<TacheResponseDto> dtos  = List.of(tacheResponseDtoPromenade, tacheResponseDtoCadeaux);

        Mockito.when(daoMock.findAll()).thenReturn(taches);
        Mockito.when(mapperMock.toTacheResponseDto(tacheCadeaux)).thenReturn(tacheResponseDtoCadeaux);
        Mockito.when(mapperMock.toTacheResponseDto(tachePromenade)).thenReturn(tacheResponseDtoPromenade);
        assertEquals(dtos, service.trouverToutes());


    }


    private static Tache creeTachePromenade() {
        Tache t = new Tache();
        t.setId(2);
        t.setDateLimite(LocalDate.of(2024, 8, 12));
        t.setLibelle("promener le chien");
        t.setNiveau(Priorite.HAUT);
        t.setTermine(true);
        return t;
    }

    private static TacheResponseDto creeTacheResponseDtoPromener() {
        return   new TacheResponseDto(2, "promener le chien", LocalDate.of(2024, 8, 12), Priorite.HAUT, true);
    }
    @DisplayName("si ajouter(null) exception levee, si la tache n'est pas nulle ca veut dire")
    @Test
    void testAjouteTacheNullr(){
        assertThrows(TacheException.class, ()->service.ajouter(null));
    }

    @DisplayName("si libelle est  nulle dans tacheRequestDto, avec libelle null et pas vide")
    @Test
    void testAjouterTacheSansLibelle(){
        TacheRequestDto tacheRequestDto=new TacheRequestDto(null, LocalDate.now(), Priorite.BAS,true);
        assertThrows(TacheException.class, ()->service.ajouter(tacheRequestDto));
    }

    @DisplayName("si libelle est vide , blank")
    @Test
    void testAjouterTacheLibelleBlank(){
        TacheRequestDto tacheRequestDto=new TacheRequestDto("\t", LocalDate.now(), Priorite.BAS,true);
        assertThrows(TacheException.class, ()->service.ajouter(tacheRequestDto));
    }

    @DisplayName("dateLimite nulle, sans date limite")
    @Test
    void testAjouterTacheDateLimiteNull(){
        TacheRequestDto tacheRequestDto=new TacheRequestDto("acheter", null, Priorite.BAS,true);
        assertThrows(TacheException.class, ()->service.ajouter(tacheRequestDto));
    }

    @DisplayName("priorite nulle")
    @Test
    void testAjouterTachePrioriteNull(){
        TacheRequestDto tacheRequestDto=new TacheRequestDto("acheter", LocalDate.now(), null,true);
        assertThrows(TacheException.class, ()->service.ajouter(tacheRequestDto));
    }
    @DisplayName("termine nulle")
    @Test
    void testAjouterTacheTermineNull(){
        TacheRequestDto tacheRequestDto=new TacheRequestDto("acheter", LocalDate.now(), Priorite.BAS,null);
        assertThrows(TacheException.class, ()->service.ajouter(tacheRequestDto));
    }

    @DisplayName("""
    si ajouter (TacheRequesteDto ok), alors save est appelee et un tacheResponseDto est renvoiee
""")
    @Test
    void testAjouter(){
        TacheRequestDto tacheRequestDto = new TacheRequestDto("promener le chien", LocalDate.of(2024,8,12), Priorite.HAUT,true);
        Tache tacheAvantEnreg = creeTachePromenade();
        tacheAvantEnreg.setId(0);

        Tache tacheApresEnreg = creeTachePromenade();
        TacheResponseDto responseDto = creeTacheResponseDtoPromener();//ce sont objets avec lesquelles je veux travailler

        Mockito.when(mapperMock.toTache(tacheRequestDto)).thenReturn(tacheAvantEnreg);//on envoie tacheAvant Reg quand elle est applee
        Mockito.when(daoMock.save(tacheAvantEnreg)).thenReturn(tacheApresEnreg);
        Mockito.when(mapperMock.toTacheResponseDto(tacheApresEnreg)).thenReturn(responseDto);

//same si c'est le meme espace memoire, et equals si c'est la meme valeur "Joe"
        assertSame(responseDto, service.ajouter(tacheRequestDto));// on regarde si on a sauvgardé la tacheRequesteDto
        Mockito.verify(daoMock, Mockito.times(1)).save(tacheAvantEnreg);//on regarde si la methode save a ete applee 1 fois
    }

}