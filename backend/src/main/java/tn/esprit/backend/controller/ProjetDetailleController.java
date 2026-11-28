package tn.esprit.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.ProjetDetailleDto;
import tn.esprit.backend.dto.ProjetDto;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.service.IProjetDetailleService;

import java.util.List;

@RestController
@RequestMapping("/projet-detaille")
@RequiredArgsConstructor
public class ProjetDetailleController {
    private final IProjetDetailleService projetDetailleService;

    @PostMapping("/add")
    public ProjetDetailleDto addProjetDetaille(@RequestBody ProjetDetailleDto dto) {
        return toDto(projetDetailleService.addProjetDetaille(toEntity(dto)));
    }

    @PutMapping("/update")
    public ProjetDetailleDto updateProjetDetaille(@RequestBody ProjetDetailleDto dto) {
        return toDto(projetDetailleService.updateProjetDetaille(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProjetDetaille(@PathVariable Long id) {
        projetDetailleService.deleteProjetDetaille(id);
    }

    @GetMapping("/get/{id}")
    public ProjetDetailleDto getProjetDetailleById(@PathVariable Long id) {
        return toDto(projetDetailleService.getProjetDetailleById(id));
    }

    @GetMapping("/all")
    public List<ProjetDetailleDto> getAllProjetsDetailles() {
        return projetDetailleService.getAllProjetsDetailles().stream().map(this::toDto).toList();
    }

    @GetMapping("/by-projet/{projetId}")
    public List<ProjetDetailleDto> getProjetDetaillesByProjet(@PathVariable Long projetId) {
        return projetDetailleService.getProjetDetaillesByProjet(projetId).stream().map(this::toDto).toList();
    }

    @PutMapping("/assign-projet/{projetDetailleId}/{projetId}")
    public ProjetDetailleDto assignProjetDetailleToProjet(
            @PathVariable Long projetDetailleId, @PathVariable Long projetId) {
        return toDto(projetDetailleService.assignProjetDetailleToProjet(projetDetailleId, projetId));
    }

    private ProjetDetaille toEntity(ProjetDetailleDto dto) {
        ProjetDetaille detaille = ProjetDetaille.builder()
                .id(dto.id())
                .description(dto.description())
                .technologie(dto.technologie())
                .coutProvisoire(dto.coutProvisoire())
                .dateDebut(dto.dateDebut())
                .build();
        if (dto.projet() != null && dto.projet().id() != null) {
            detaille.setProjet(Projet.builder().id(dto.projet().id()).build());
        }
        return detaille;
    }

    private ProjetDetailleDto toDto(ProjetDetaille detaille) {
        if (detaille == null) return null;
        Projet projet = detaille.getProjet();
        ProjetDto projetDto = projet == null ? null : new ProjetDto(projet.getId(), projet.getSujet());
        return new ProjetDetailleDto(detaille.getId(), detaille.getDescription(), detaille.getTechnologie(),
                detaille.getCoutProvisoire(), detaille.getDateDebut(), projetDto);
    }
}
