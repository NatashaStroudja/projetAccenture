package com.accenture.service;

import com.accenture.exception.TacheException;
import com.accenture.model.Priorite;
import com.accenture.repository.TacheDao;
import com.accenture.repository.entity.Tache;
import com.accenture.service.dto.TacheRequestDto;
import com.accenture.service.dto.TacheResponseDto;
import com.accenture.service.mapper.TacheMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TacheServiceImpl implements TacheService {
    public static final String ID_NON_PRESENT = "Id non present!";
    private final TacheDao tacheDao;
    private final TacheMapper tacheMapper;

    public TacheServiceImpl(TacheDao tacheDao, TacheMapper tacheMapper) {
        this.tacheDao = tacheDao;
        this.tacheMapper = tacheMapper;
    }

    @Override
    public TacheResponseDto trouver(int id) throws EntityNotFoundException {
        Optional<Tache> optTache = tacheDao.findById(id);
        if (optTache.isEmpty())
            throw new EntityNotFoundException("L'id non present");
        Tache tache = optTache.get();
        return tacheMapper.toTacheResponseDto(tache);
    }

    @Override
    public List<TacheResponseDto> trouverToutes() {
        return tacheDao.findAll().stream()
                .map(tacheMapper::toTacheResponseDto)//map pour tranformer un type
                .toList();
    }


    @Override
    public TacheResponseDto ajouter(TacheRequestDto tacheRequestDto) throws TacheException {
        verifierTache(tacheRequestDto);
        Tache tache = tacheMapper.toTache(tacheRequestDto);
        //transformer TACHrEQUESTEdTO EN UNE TACHE

        //Tache tacheEnregistree = tacheDao.save(tache);
        //return tacheEnregistree; correcpond à  return tacheDao.save(tache);

        Tache tacheRetour = tacheDao.save(tache);
        //tache on doit devoire transformer en tacheResponseDto
        return tacheMapper.toTacheResponseDto(tacheRetour);
    }


    @Override
    public TacheResponseDto modifier(int id, TacheRequestDto tacheRequestDto) throws TacheException, EntityNotFoundException {
        if (!tacheDao.existsById(id))//on verifie si id existe pour modifier
            throw new EntityNotFoundException(ID_NON_PRESENT);
        verifierTache(tacheRequestDto);
        Tache tache = tacheMapper.toTache(tacheRequestDto);
        tache.setId(id);
        Tache tacheEnreg = tacheDao.save(tache);
        return tacheMapper.toTacheResponseDto(tacheEnreg);
    }

    @Override
    public TacheResponseDto modifierPartiellement(int id, TacheRequestDto tacheRequestDto) throws TacheException, EntityNotFoundException {
        Optional<Tache> optTache = tacheDao.findById(id);
        if (optTache.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);//on va voir dans bd si la tache existe
        Tache tacheExistente = optTache.get();//on recupere tache*/
        Tache nouvelle = tacheMapper.toTache(tacheRequestDto);

        remplacer(nouvelle, tacheExistente);
        Tache tacheEnreg = tacheDao.save(tacheExistente);
        return tacheMapper.toTacheResponseDto(tacheEnreg);
    }


    @Override
    public void supprimer(int id) throws EntityNotFoundException {
        if (tacheDao.existsById(id))
            tacheDao.deleteById(id);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
    }

    @Override
    public List<TacheResponseDto> rechercher(String libelle, LocalDate dateLimite, Priorite priorite, Boolean termine) {
        List<Tache> liste = null;
        if (libelle != null)
            liste = tacheDao.findByLibelleContaining(libelle);
        else if (dateLimite != null)
            liste = tacheDao.findByDateLimite(dateLimite);
        else if (priorite != null)
            liste = tacheDao.findByNiveau(priorite);
        else if (termine != null)
            liste = tacheDao.findByTermine(termine);

        if (liste == null)
            throw new TacheException("Un critere de recherche est obligatoire");
        return liste.stream()
                .map(tacheMapper::toTacheResponseDto)
                .toList();

    }

    //Méthoes privées
    private static void verifierTache(TacheRequestDto tacheRequestDto) throws TacheException, EntityNotFoundException {
        if (tacheRequestDto == null)
            throw new TacheException("La tache est nulle");
        if (tacheRequestDto.libelle() == null || tacheRequestDto.libelle().isBlank())
            throw new TacheException("Le libelle est obligatoire");
        if (tacheRequestDto.niveau() == null)
            throw new TacheException("le neveau est absent");
        if (tacheRequestDto.dateLimite() == null)
            throw new TacheException("La date limite est obligatoite");
        if ((tacheRequestDto.termine() == null))
            throw new TacheException("Le 'terminé' est obligatoire");
    }

    private static void remplacer(Tache tache, Tache tacheExistante) {
        if (tacheExistante.getTermine() != null)
            tacheExistante.setTermine(tacheExistante.getTermine());
        if (tacheExistante.getLibelle() != null)
            tacheExistante.setLibelle(tache.getLibelle());
        if (tacheExistante.getNiveau() != null)
            tacheExistante.setNiveau(tache.getNiveau());
        if (tacheExistante.getDateLimite() != null)
            tacheExistante.setDateLimite(tache.getDateLimite());
    }

}
